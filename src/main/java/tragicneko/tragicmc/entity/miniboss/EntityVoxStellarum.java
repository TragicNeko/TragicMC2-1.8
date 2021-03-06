package tragicneko.tragicmc.entity.miniboss;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tragicneko.tragicmc.TragicConfig;
import tragicneko.tragicmc.TragicPotion;
import tragicneko.tragicmc.entity.mob.EntityNorVox;
import tragicneko.tragicmc.entity.projectile.EntityStarShard;
import tragicneko.tragicmc.util.DamageHelper;

public class EntityVoxStellarum extends EntityNorVox implements TragicMiniBoss {
	
	public static final int DW_SPIN_TICKS = 25;
	public static final int DW_HEAL_TICKS = 26;

	public EntityVoxStellarum(World par1World) {
		super(par1World);
		this.stepHeight = 2.0F;
		this.experienceValue = 220;
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		double[] voxStellarumStats = TragicConfig.getMobStat("voxStellarumStats").getStats();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(voxStellarumStats[0]);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(voxStellarumStats[1]);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(voxStellarumStats[2]);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(voxStellarumStats[3]);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(voxStellarumStats[4]);
	}

	@Override
	public boolean isMobVariant()
	{
		return false;
	}

	@Override
	public boolean canRenderOnFire()
	{
		return !this.isHealing() && this.isBurning();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}

	@Override
	public float getBrightness(float par1)
	{
		return 1.0F;
	}

	@Override
	public void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(DW_SPIN_TICKS, Integer.valueOf(0));
		this.dataWatcher.addObject(DW_HEAL_TICKS, Integer.valueOf(0));
	}

	@Override
	protected void setNorVoxType(byte b)
	{
		this.dataWatcher.updateObject(DW_NOR_VOX_TYPE, (byte) 0);
	}

	public int getSpinTicks()
	{
		return this.dataWatcher.getWatchableObjectInt(DW_SPIN_TICKS);
	}

	private void setSpinTicks(int i)
	{
		this.dataWatcher.updateObject(DW_SPIN_TICKS, i);
	}

	private void decrementSpinTicks()
	{
		int pow = this.getSpinTicks();
		this.setSpinTicks(--pow);
	}

	public boolean isSpinning()
	{
		return this.getSpinTicks() > 0;
	}

	public int getHealTicks()
	{
		return this.dataWatcher.getWatchableObjectInt(DW_HEAL_TICKS);
	}

	private void setHealTicks(int i)
	{
		this.dataWatcher.updateObject(DW_HEAL_TICKS, i);
	}

	public boolean isHealing()
	{
		return this.getHealTicks() > 0;
	}

	private void decrementHealingTicks()
	{
		int pow = this.getHealTicks();
		this.setHealTicks(--pow);
	}

	public float getRotationSpeed()
	{
		return ((float) Math.cos((2.115F / (Math.PI * 2.0F)) * (this.getSpinTicks() / 100.0F) + 0.125F)) * 2.35F;
	}

	@Override
	public void onLivingUpdate()
	{
		if (this.isSpinning()) this.motionY = -0.1;
		
		super.onLivingUpdate();

		if (this.worldObj.isRemote)
		{
			if (this.isSpinning())
			{
				this.worldObj.spawnParticle(EnumParticleTypes.CRIT,
						this.posX + (this.rand.nextDouble() - 0.5D) * this.width * 2.5D,
						this.posY + this.rand.nextDouble() * this.height,
						this.posZ + (this.rand.nextDouble() - 0.5D) * this.width * 2.5D,
						(this.rand.nextDouble() - 0.6D) * 0.1D,
						this.rand.nextDouble() * 0.1D,
						(this.rand.nextDouble() - 0.6D) * 0.1D);
			}
			
			if (this.getHealTicks() >= 100)
			{
				for (byte i = 0; i < 8; i++)
				{
					this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + (rand.nextDouble() - rand.nextDouble()) * this.width * 2.5, this.posY + rand.nextDouble() * this.height,
							this.posZ + (rand.nextDouble() - rand.nextDouble()) * this.width * 2.5, rand.nextFloat() * 2.25F, rand.nextFloat() * 2.25F, rand.nextFloat() * 2.25F);
				}
			}
		}
		else
		{
			if (this.isSpinning())
			{
				this.decrementSpinTicks();
				if (this.isFiring()) this.setFiringTicks(0);
			}
			if (this.isHealing()) this.decrementHealingTicks();
			if (TragicConfig.getBoolean("allowStun") && this.isPotionActive(TragicPotion.Stun.id) || this.getAttackTarget() == null) this.setSpinTicks(0);


			double modifier = (Math.sin((2.115D / (Math.PI * 2)) * (this.getSpinTicks() / 100.0D) - 0.125D)) * 0.235D;
			AttributeModifier mod2 = new AttributeModifier(UUID.fromString("e20a064f-7022-4c64-99A2-181d3ac9eb17"), "voxStellarumSpinning", modifier, 0);

			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(mod2);

			if (this.isSpinning() && this.getSpinTicks() >= 100 && this.getSpinTicks() <= 800)
			{
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(mod2);
				this.setSprinting(true);
			}
			else
			{
				this.setSprinting(false);
			}

			if (this.getHealth() <= this.getMaxHealth() / 2 && !this.isFiring() && !this.isSpinning() && !this.isHealing() && this.ticksExisted % 20 == 0 && rand.nextInt(8) == 0 && TragicConfig.getBoolean("voxStellarumHealing"))
			{
				this.setHealTicks(500);
			}

			if (!this.isHealing() && !this.isFiring() && !this.isSpinning() && this.ticksExisted % 20 == 0 && rand.nextInt(16) == 0 && this.getAttackTarget() != null && TragicConfig.getBoolean("voxStellarumSpinAttack")) this.setSpinTicks(1000);

			if (this.isHealing() && this.getHealTicks() >= 100)
			{
				this.motionY = this.onGround ? 0.0D : -0.05D;
				if (Math.abs(this.motionX) > 0.25D) this.motionX *= 0.225D;
				if (Math.abs(this.motionZ) > 0.25D) this.motionZ *= 0.225D;

				if (this.ticksExisted % 4 == 0) this.heal(1.0F);

				if (this.getHealth() >= this.getMaxHealth()) this.setHealTicks(0);
				if (this.getHealth() >= this.getMaxHealth() || this.getHealTicks() == 100)
				{
					this.playSound("tragicmc:random.truncatedbeep", 1.4F, 0.2F);
				}
				else
				{
					this.playSound("tragicmc:random.truncatedbeep", 0.2F, 1.98F);
				}
			}

			if (this.isSpinning() && this.getAttackTarget() != null && this.ticksExisted % 2 == 0)
			{
				EntityLivingBase entity = this.getAttackTarget();
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(mod); //ensure firing debuff is removed

				double d0 = entity.posX - this.posX;
				double d1 = entity.posZ - this.posZ;
				double d2 = entity.posY - this.posY;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
				double d3 = 0.5D;
				double d4 = this.getSpinTicks() >= 100 && this.getSpinTicks() <= 800 ? 0.22786D : 0.1433467D;

				this.motionX = d0 / f2 * d3 * 0.100000011920929D + entity.motionX * d4;
				this.motionZ = d1 / f2 * d3 * 0.100000011920929D + entity.motionZ * d4;
				if (this.motionY >= 0.25D) this.motionY = -0.15D;

				if (this.getHealth() <= this.getMaxHealth() / 3 && this.ticksExisted % 10 == 0) this.shootProjectiles();
				
				double dm = 2.0D;
				List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(dm, dm, dm));
				Iterator iterator = list.iterator();

				while (iterator.hasNext())
				{
					EntityLivingBase entity2 = (EntityLivingBase) iterator.next();

					if (this.canEntityBeSeen(entity2) && entity2 != this)
					{
						entity2.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
					}
				}
			}
		}
	}

	@Override
	protected void shootProjectiles()
	{
		if (!TragicConfig.getBoolean("norVoxProjectiles")) return;
		double d0 = this.getAttackTarget().posX - this.posX;
		double d1 = this.getAttackTarget().posY - this.posY;
		double d2 = this.getAttackTarget().posZ - this.posZ;

		float f1 = MathHelper.sqrt_float(this.getDistanceToEntity(this.getAttackTarget())) * 0.625F;
		int blah = this.isSpinning() ? 10 : 4;

		for (int i = 0; i < blah; i++)
		{
			if (this.isSpinning())
			{
				d0 = rand.nextInt(16) - rand.nextInt(16);
				d1 = rand.nextInt(3) - rand.nextInt(3);
				d2 = rand.nextInt(16) - rand.nextInt(16);
			}

			EntityStarShard shard = new EntityStarShard(this.worldObj, this, d0 + this.rand.nextGaussian() * f1, d1, d2 + this.rand.nextGaussian() * f1);
			shard.posX = this.posX + (0.115D * d0);
			shard.posY = this.posY + (this.height * 3 / 4);
			shard.posZ = this.posZ + (0.115D * d2);
			this.worldObj.spawnEntityInWorld(shard);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (this.worldObj.isRemote) return false;

		if (this.isSpinning() && this.getSpinTicks() >= 100 && this.getSpinTicks() <= 800)
		{
			par2 /= 4;
			if (par1DamageSource.isProjectile()) par2 = 0;
		}

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (this.isHealing()) return false;

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
		{
			if (this.isSpinning() && par1Entity instanceof EntityLivingBase && this.getSpinTicks() >= 100 && this.getSpinTicks() <= 800)
			{
				((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, rand.nextInt(60) + 120));

				par1Entity.motionX += this.motionX;
				par1Entity.motionZ += this.motionZ;
				par1Entity.motionY += 0.35D;
			}
		}
		return flag;
	}

	@Override
	public void collideWithEntity(Entity entity)
	{
		super.collideWithEntity(entity);
	}

	@Override
	public int getTotalArmorValue()
	{
		return TragicConfig.getMobStat("voxStellarumStats").getArmorValue();
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance ins, IEntityLivingData data)
	{
		if (!this.worldObj.isRemote) this.setTextureID((byte) rand.nextInt(8));
		return super.onInitialSpawn(ins, data);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		if (tag.hasKey("spinTicks")) this.setSpinTicks(tag.getInteger("spinTicks"));
		if (tag.hasKey("healingTicks")) this.setHealTicks(tag.getInteger("healingTicks"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setInteger("spinTicks", this.getSpinTicks());
		tag.setInteger("healingTicks", this.getHealTicks());
	}

	@Override
	protected boolean isChangeAllowed() {
		return false;
	}

	@Override
	public Class getLesserForm() {
		return EntityNorVox.class;
	}

	@Override
	public String getLivingSound()
	{
		return "tragicmc:mob.cryse.glass";
	}

	@Override
	public String getHurtSound()
	{
		return "tragicmc:mob.cryse.hit";
	}

	@Override
	public String getDeathSound()
	{
		return "tragicmc:mob.cryse.break";
	}

	@Override
	public float getSoundPitch()
	{
		return 1.0F;
	}

	@Override
	public float getSoundVolume()
	{
		return 0.6F;
	}

	@Override
	public int getTalkInterval()
	{
		return super.getTalkInterval();
	}

	@Override
	protected void playStepSound(BlockPos pos, Block block)
	{
		
	}
	
	@Override
	public int getDropAmount()
	{
		return 5;
	}
	
	@Override
	protected void updateSize() {
		this.setSize(2.475F, 2.725F);
	}
}
