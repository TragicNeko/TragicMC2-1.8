package tragicneko.tragicmc.entity.alpha;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import tragicneko.tragicmc.TragicAchievements;
import tragicneko.tragicmc.TragicBlocks;
import tragicneko.tragicmc.TragicConfig;
import tragicneko.tragicmc.TragicEntities;
import tragicneko.tragicmc.TragicItems;
import tragicneko.tragicmc.TragicPotion;
import tragicneko.tragicmc.entity.boss.TragicBoss;
import tragicneko.tragicmc.entity.mob.EntityNanoSwarm;
import tragicneko.tragicmc.entity.mob.EntitySeeker;
import tragicneko.tragicmc.util.WorldHelper;

public class EntityOverlordCocoon extends TragicBoss {

	public static final int DW_PHASE_TICKS = 20;

	private boolean phaseChange = false;
	private float phaseDamage;
	private ArrayList<EntitySeeker> seekers = new ArrayList<EntitySeeker>();

	public EntityOverlordCocoon(World par1World) {
		super(par1World);
		this.setSize(5.385F, 5.325F);
		this.stepHeight = 2.0F;
		this.experienceValue = 50;
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true, false, EntityOverlordCombat.nonSynapseTarget));
		this.isImmuneToFire = true;
	}

	@Override
	public void setAir(int i){}

	@Override
	protected void despawnEntity() {}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public void fall(float dist, float multi) {}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return TragicEntities.Synapse;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		if (par1DamageSource.getEntity() instanceof EntityPlayerMP) ((EntityPlayerMP) par1DamageSource.getEntity()).triggerAchievement(TragicAchievements.overlord2);
		List<EntityPlayerMP> list = this.worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, this.getEntityBoundingBox().expand(24.0, 24.0, 24.0));
		if (!list.isEmpty())
		{
			for (EntityPlayerMP mp : list)
			{
				mp.triggerAchievement(TragicAchievements.overlord2);
			}
		}
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		double[] overlordCocoonStats = TragicConfig.getMobStat("overlordCocoonStats").getStats();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(overlordCocoonStats[0]);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(overlordCocoonStats[1]);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(overlordCocoonStats[2]);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(overlordCocoonStats[3]);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(overlordCocoonStats[4]);
	}

	@Override
	public int getTotalArmorValue()
	{
		return TragicConfig.getMobStat("overlordCocoonStats").getArmorValue();
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(DW_PHASE_TICKS, Integer.valueOf(0)); //phaseTicks
	}

	private void setPhaseTicks(int i)
	{
		this.dataWatcher.updateObject(DW_PHASE_TICKS, i);
	}

	public int getPhaseTicks()
	{
		return this.dataWatcher.getWatchableObjectInt(DW_PHASE_TICKS);
	}

	@Override
	public void onLivingUpdate()
	{
		this.rotationYaw = this.rotationPitch = this.prevRotationPitch = this.prevRotationYaw = 0;
		this.motionX = this.motionZ = 0D;
		this.motionY = -1D;
		super.onLivingUpdate();

		if (this.worldObj.isRemote) return;

		EntitySeeker seek;
		for (int i = 0; i < this.seekers.size(); i++)
		{
			seek = this.seekers.get(i);
			if (seek.isDead || seek.getHealth() <= 0 || this.getDistanceToEntity(seek) >= 32.0) this.seekers.remove(seek);
		}

		List<EntitySeeker> lst = this.worldObj.getEntitiesWithinAABB(EntitySeeker.class, this.getEntityBoundingBox().expand(32.0, 32.0, 32.0));

		for (EntitySeeker sk : lst)
		{
			if (sk.getOwner() == null)
			{
				sk.setOwner(this);
				this.seekers.add(sk);
			}
			else if (sk.getOwner() == this && !this.seekers.contains(sk))
			{
				this.seekers.add(sk);
			}
		}

		if (this.seekers.isEmpty() && this.ticksExisted % 100 == 0 && !TragicConfig.getBoolean("overlordPhases")) this.spawnSeekers();

		if (this.seekers.isEmpty() && this.getPhaseTicks() == 0 && this.deathTime == 0 && TragicConfig.getBoolean("overlordPhases")) this.setPhaseTicks(200);

		if (this.getPhaseTicks() > 0 && TragicConfig.getBoolean("overlordPhases"))
		{
			this.setPhaseTicks(this.getPhaseTicks() - 1);

			if (TragicConfig.getBoolean("overlordGas"))
			{
				List<BlockPos> lst2 = WorldHelper.getBlocksInSphericalRange(this.worldObj, 3.5, this.posX + rand.nextInt(4) - rand.nextInt(4), this.posY, this.posZ + rand.nextInt(4) - rand.nextInt(4));
				for (BlockPos coords : lst2)
				{
					if (World.doesBlockHaveSolidTopSurface(this.worldObj, coords.down()) && EntityOverlordCore.replaceableBlocks.contains(this.worldObj.getBlockState(coords).getBlock()))
					{
						this.worldObj.setBlockState(coords, TragicBlocks.CorruptedGas.getDefaultState());
					}
				}
			}

			if (this.phaseChange)
			{
				this.phaseChange = false;
				this.setPhaseTicks(0);
				for (EntitySeeker sk : this.seekers) sk.setDead();
				this.spawnSeekers();
				this.phaseDamage = 0F;

				if (this.getHealth() > 0F)
				{
					float f = this.getMaxHealth() / 5;
					switch (this.getCurrentPhase())
					{
					case 0:
						f *= 4;
						break;
					case 1:
						f *= 3;
						break;
					case 2:
						f *= 2;
						break;
					case 3:
						break;
					case 4:
						f = 0;
						break;
					default:
						break;
					}
					this.setHealth(f);
				}

				if (TragicConfig.getBoolean("allowDivinity"))
				{
					List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(64.0, 64.0, 64.0));
					for (Entity e : list)
					{
						if (e instanceof EntityLivingBase && ((EntityLivingBase) e).isPotionActive(TragicPotion.Divinity)) ((EntityLivingBase) e).removePotionEffect(TragicPotion.Divinity.id);
					}
				}
				if (TragicConfig.getBoolean("allowMobSounds")) this.worldObj.playSoundAtEntity(this, "tragicmc:boss.overlordcocoon.phasecomplete", 1.8F, 1.0F);
			}
			else
			{
				if (this.getPhaseTicks() == 199)
				{
					if (TragicConfig.getBoolean("allowDivinity"))
					{
						List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(64.0, 64.0, 64.0));
						for (Entity e : list)
						{
							if (e instanceof EntityLivingBase && !((EntityLivingBase) e).isPotionActive(TragicPotion.Divinity)) ((EntityLivingBase) e).addPotionEffect(new PotionEffect(TragicPotion.Divinity.id, 200));
						}
					}
				}

				if (this.getPhaseTicks() == 0)
				{
					if (this.getHealth() > 0F)
					{
						float f = this.getMaxHealth() / 5;
						switch (this.getCurrentPhase())
						{
						case 0:
							f *= 5;
							break;
						case 1:
							f *= 4;
							break;
						case 2:
							f *= 3;
							break;
						case 3:
							f *= 2;
							break;
						case 4:
						default:
							break;
						}
						this.setHealth(f);
					}
					this.phaseDamage = 0F;

					for (EntitySeeker sk : this.seekers) sk.setDead();
					this.spawnSeekers();

					if (TragicConfig.getBoolean("allowDivinity"))
					{
						List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(64.0, 64.0, 64.0));
						for (Entity e : list)
						{
							if (e instanceof EntityLivingBase && ((EntityLivingBase) e).isPotionActive(TragicPotion.Divinity)) ((EntityLivingBase) e).removePotionEffect(TragicPotion.Divinity.id);
						}
					}

					if (TragicConfig.getBoolean("allowMobSounds")) this.worldObj.playSoundAtEntity(this, "tragicmc:boss.overlordcocoon.phasefail", 1.8F, 1.0F);
				}

				if (TragicConfig.getBoolean("allowMobSounds") && this.getPhaseTicks() % 10 == 0) this.worldObj.playSoundAtEntity(this, "tragicmc:boss.overlordcocoon.wah", 1.4F, 1.0F);
			}

		}
	}

	@Override
	public void addPotionEffect(PotionEffect pe) {}

	/**
	 * Five phases, 0 is full health, 4 is below 1/5 health
	 * @return
	 */
	public int getCurrentPhase()
	{
		float hp = this.getHealth();
		float maxFifth = this.getMaxHealth() / 5;
		return hp <= maxFifth ? 4 : (hp <= maxFifth * 2 ? 3 : (hp <= maxFifth * 3 ? 2 : (hp <= maxFifth * 4 ? 1 : 0)));
	}

	public void spawnSeekers()
	{
		if (!TragicConfig.getBoolean("overlordSeekers")) return;
		int i;

		switch(this.getCurrentPhase())
		{
		case 0:
			i = 2 + rand.nextInt(2);
			break;
		case 1:
			i = 2 + rand.nextInt(2);
			break;
		case 2:
			i = 3 + rand.nextInt(3);
			break;
		case 3:
			i = 3 + rand.nextInt(3);
			break;
		case 4:
			i = 4 + rand.nextInt(4);
			break;
		default:
			i = 1;
			break;
		}

		for (int x = 0; x < i; x++)
		{
			EntitySeeker seeker = new EntitySeeker(this.worldObj);
			seeker.setPosition(this.posX + rand.nextInt(12) - rand.nextInt(12), this.posY + rand.nextInt(12), this.posZ + rand.nextInt(12) - rand.nextInt(12));
			seeker.setOwner(this);
			this.worldObj.spawnEntityInWorld(seeker);
			this.seekers.add(seeker);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource src, float dmg)
	{
		if (src.isProjectile() || this.worldObj.isRemote) return false;

		if (src.getEntity() instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) src.getEntity();
			boolean flag = TragicConfig.getBoolean("allowDivinity") && entity.isPotionActive(TragicPotion.Divinity);

			if (rand.nextInt(4) == 0 && this.getAttackTarget() != entity && entity.getCreatureAttribute() != TragicEntities.Synapse) this.setAttackTarget(entity);

			if (flag && this.hurtResistantTime == 0 || !TragicConfig.getBoolean("allowDivinity") && entity.getCreatureAttribute() != TragicEntities.Synapse && this.hurtResistantTime == 0 ||
					entity.getHeldItem() != null && entity.getHeldItem().getItem() == TragicItems.SwordOfJustice || src.canHarmInCreative() || !TragicConfig.getBoolean("overlordDivineWeakness") ||
					!TragicConfig.getBoolean("overlordPhases"))
			{
				this.phaseDamage += MathHelper.clamp_float(dmg - this.getTotalArmorValue(), 0F, (float) TragicConfig.getInt("bossDamageCap"));

				if (this.getCurrentPhase() < 4)
				{
					float f = this.getMaxHealth() / 5;
					switch (this.getCurrentPhase())
					{
					case 0:
						f *= 4;
						break;
					case 1:
						f *= 3;
						break;
					case 2:
						f *= 2;
						break;
					case 3:
						break;
					case 4:
						f = 0;
						break;
					default:
						break;
					}

					if (this.getHealth() - MathHelper.clamp_float(dmg - this.getTotalArmorValue(), 0F, (float) TragicConfig.getInt("bossDamageCap")) <= f && f > 0 || this.phaseDamage >= this.getMaxHealth() / 5)
					{
						this.phaseChange = true;
						dmg = 0;
						this.phaseDamage = 0F;
					}
				}

				if (rand.nextBoolean() && this.worldObj.getEntitiesWithinAABB(EntityNanoSwarm.class, this.getEntityBoundingBox().expand(64.0, 64.0, 64.0D)).size() < 16 && this.getCurrentPhase() > 1 && TragicConfig.getBoolean("allowNanoSwarm") && TragicConfig.getBoolean("overlordNanoSwarms"))
				{
					EntityNanoSwarm swarm = new EntityNanoSwarm(this.worldObj);
					swarm.setPosition(this.posX + rand.nextInt(6) - rand.nextInt(6), this.posY, this.posZ + rand.nextInt(6) - rand.nextInt(6));
					this.worldObj.spawnEntityInWorld(swarm);
				}

				return super.attackEntityFrom(src, dmg);
			}
		}
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		if (this.worldObj.isRemote) return false;

		boolean flag = super.attackEntityAsMob(entity);
		return flag;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance ins, IEntityLivingData data)
	{
		if (!this.worldObj.isRemote)
		{
			if (TragicConfig.getBoolean("overlordClearSpace"))
			{
				List<BlockPos> lst = WorldHelper.getBlocksInSphericalRange(this.worldObj, 10.5, this.posX, this.posY + 1, this.posZ);

				for (BlockPos coord: lst)
				{
					if (this.posY > coord.getY() + 1 && this.worldObj.getBlockState(coord).getBlock().getBlockHardness(this.worldObj, coord) > 0F) this.worldObj.setBlockToAir(coord);
				}

				lst = WorldHelper.getBlocksInSphericalRange(this.worldObj, 10.0, this.posX, this.posY - 1, this.posZ);

				for (BlockPos coords : lst)
				{
					if (this.posY >= coords.getY() + 1 && (!EntityOverlordCore.ignoredBlocks.contains(this.worldObj.getBlockState(coords).getBlock()) && this.worldObj.getBlockState(coords).getBlock().getBlockHardness(this.worldObj, coords) > 0F || this.worldObj.getBlockState(coords).getBlock() == Blocks.air)) this.worldObj.setBlockState(coords, !TragicConfig.getBoolean("allowNonMobBlocks") ? Blocks.obsidian.getDefaultState() : TragicBlocks.CelledBlock.getDefaultState());
				}
			}
			this.spawnSeekers();
			List<EntitySeeker> list = this.worldObj.getEntitiesWithinAABB(EntitySeeker.class, this.getEntityBoundingBox().expand(32.0, 32.0, 32.0));

			for (EntitySeeker sk : list)
			{
				if (sk.getOwner() == null)
				{
					sk.setOwner(this);
					this.seekers.add(sk);
				}
				else if (sk.getOwner() == this && !this.seekers.contains(sk))
				{
					this.seekers.add(sk);
				}
			}
		}
		return super.onInitialSpawn(ins, data);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		if (tag.hasKey("phase") && tag.hasKey("phaseDamage"))
		{
			float f = this.getMaxHealth() / 5;
			switch (tag.getInteger("phase"))
			{
			case 0:
				f *= 5;
				break;
			case 1:
				f *= 4;
				break;
			case 2:
				f *= 3;
				break;
			case 3:
				f *= 2;
				break;
			case 4:
			default:
				break;
			}
			this.setHealth(f - tag.getFloat("phaseDamage"));
		}
		if (tag.hasKey("phaseTicks")) this.setPhaseTicks(tag.getInteger("phaseTicks"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setInteger("phase", this.getCurrentPhase());
		tag.setBoolean("phaseChange", this.phaseChange);
		tag.setFloat("phaseDamage", this.phaseDamage);
		tag.setInteger("phaseTicks", this.getPhaseTicks());
	}

	@Override
	protected void onDeathUpdate()
	{
		++this.deathTime;
		this.hurtResistantTime = 0;
		this.hurtTime = 0;

		if (this.deathTime >= 200)
		{
			this.deathTime = 1;

			int i;
			if (!this.worldObj.isRemote && this.recentlyHit > 0)
			{
				i = this.getExperiencePoints(this.attackingPlayer);

				while (i > 0)
				{
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
				}
			}

			this.setDead();

			for (int ji = 0; ji < 20; ++ji)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height * 2.0F, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, 0.1, 0.1, 0.1);
			}

			if (!this.worldObj.isRemote)
			{
				List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(16.0, 16.0, 16.0));
				for (Entity e : list)
				{
					if (e instanceof EntityLivingBase && ((EntityLivingBase) e).getCreatureAttribute() == TragicEntities.Synapse)
					{
						e.setDead();
					}
				}

				if (TragicConfig.getBoolean("overlordTransformation"))
				{
					EntityOverlordCombat combat = new EntityOverlordCombat(this.worldObj);
					combat.setPosition(this.posX, this.posY, this.posZ);
					combat.setTransforming();
					this.worldObj.spawnEntityInWorld(combat);
				}
			}
		}

		if (!this.worldObj.isRemote) for (EntitySeeker sk : this.seekers) sk.setDead();

		for (int j = 0; j < 40; ++j)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.rand.nextFloat() * this.width * 5.0F - this.width, this.posY + this.rand.nextFloat() * this.height * 2.0F, this.posZ + this.rand.nextFloat() * this.width * 5.0F - this.width, 0, 0, 0);
		}

		for (int ji = 0; ji < 40; ++ji)
		{
			this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.rand.nextFloat() * this.width * 5.0F - this.width, this.posY + this.rand.nextFloat() * this.height * 2.0F, this.posZ + this.rand.nextFloat() * this.width * 5.0F - this.width, 0.1, 0.1, 0.1);
		}
	}

	@Override
	public String getLivingSound()
	{
		return TragicConfig.getBoolean("allowMobSounds") ? "tragicmc:boss.overlordcocoon.living" : null;
	}

	@Override
	public String getHurtSound()
	{
		return TragicConfig.getBoolean("allowMobSounds") ? "tragicmc:boss.overlordcocoon.hurt" : super.getHurtSound();
	}

	@Override
	public String getDeathSound()
	{
		return TragicConfig.getBoolean("allowMobSounds") ? "tragicmc:boss.overlordcocoon.death" : null;
	}

	@Override
	public float getSoundPitch()
	{
		return 1.0F;
	}

	@Override
	public float getSoundVolume()
	{
		return 1.4F;
	}

	@Override
	public int getTalkInterval()
	{
		return super.getTalkInterval();
	}
}
