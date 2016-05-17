package tragicneko.tragicmc.entity.mob;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import tragicneko.tragicmc.TragicConfig;
import tragicneko.tragicmc.TragicEntities;
import tragicneko.tragicmc.entity.EntityLock;
import tragicneko.tragicmc.entity.alpha.EntityOverlordCombat;
import tragicneko.tragicmc.entity.miniboss.TragicMiniBoss;
import tragicneko.tragicmc.util.DamageHelper;

public class EntityLockbot extends TragicMob {

	private EntityLivingBase prevTarget;

	public EntityLockbot(World par1World) {
		super(par1World);
		this.setSize(0.625F, 0.725F);
		this.stepHeight = 1.0F;
		this.experienceValue = 50;
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true, false, EntityOverlordCombat.nonSynapseTarget));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		double[] lockbotStats = TragicConfig.getMobStat("lockbotStats").getStats();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(lockbotStats[0]);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(lockbotStats[1]);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(lockbotStats[2]);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(lockbotStats[3]);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(lockbotStats[4]);
	}

	@Override
	public int getTotalArmorValue()
	{
		return TragicConfig.getMobStat("lockbotStats").getArmorValue();
	}

	@Override
	protected boolean isChangeAllowed() {
		return true; //TragicConfig.allowWarden;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return TragicEntities.Synapse;
	}

	@Override
	public void onLivingUpdate()
	{
		if (!(this instanceof TragicMiniBoss))
		{
			this.rotationYaw = this.rotationPitch = this.rotationYawHead = 0;
			this.motionX *= 0.1D;
			this.motionZ *= 0.1D;
		}
		
		super.onLivingUpdate();

		if (this.worldObj.isRemote) return;

		if (this.prevTarget == null && this.getAttackTarget() != null || this.getAttackTarget() != null && this.prevTarget != null && this.getAttackTarget() != this.prevTarget)
		{
			this.getAttackTarget().attackEntityFrom(DamageHelper.causeArmorPiercingDamageToEntity(this), (float) this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
			this.prevTarget = this.getAttackTarget();
		}

		if (this.getAttackTarget() != null && this.getDistanceToEntity(this.getAttackTarget()) <= this.getLockdownDistance() && this.getAttackTarget().riddenByEntity == null && TragicConfig.getBoolean("lockbotLockdown"))
		{
			EntityLock lock = new EntityLock(this.worldObj, this, this.getAttackTarget());
			lock.setPosition(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ);
			this.worldObj.spawnEntityInWorld(lock);
		}
	}
	
	public float getLockdownDistance() {
		return 8.0F;
	}

	@Override
	public void fall(float dist, float multi) {}

	@Override
	public void updateFallState(double par1, boolean par2, Block block, BlockPos pos) {}

	@Override
	public String getLivingSound()
	{
		return TragicConfig.getBoolean("allowMobSounds") ? "tragicmc:mob.lockbot.hum" : null;
	}

	@Override
	public String getHurtSound()
	{
		return super.getHurtSound();
	}

	@Override
	public String getDeathSound()
	{
		return TragicConfig.getBoolean("allowMobSounds") ? "tragicmc:mob.lockbot.hum" : null;
	}

	@Override
	public float getSoundPitch()
	{
		return 1.0F;
	}

	@Override
	public float getSoundVolume()
	{
		return 0.6F + rand.nextFloat() * 0.2F;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block block)
	{

	}

	@Override
	public int getTalkInterval()
	{
		return 2;
	}
}
