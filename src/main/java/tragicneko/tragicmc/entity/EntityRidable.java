package tragicneko.tragicmc.entity;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tragicneko.tragicmc.proxy.ClientProxy;

public abstract class EntityRidable extends EntityCreature {

	protected int attackCooldown = 100;
	protected Entity previousRider = null;

	public EntityRidable(World par1World) {
		super(par1World);
	}

	public abstract void useAttack(int attackType);

	public void useAttackViaMob(int attackType, EntityLivingBase target) {

	}

	public abstract boolean canAttack();

	@Override
	public void onLivingUpdate() {
		if (this.getAttackTarget() == this.riddenByEntity) this.setAttackTarget(null);

		super.onLivingUpdate();

		if (this.riddenByEntity != this.previousRider)
		{
			this.onRiderChange();
			this.previousRider = this.riddenByEntity;
		}
	}

	public abstract void onRiderChange();

	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		if (itemstack == null && this.riddenByEntity == null) {
			player.mountEntity(this);

			if (this.worldObj.isRemote) {
				player.addChatMessage(new ChatComponentText("Hit " + Keyboard.getKeyName(ClientProxy.openAmuletGui.getKeyCode()) + " to use your Primary attack!"));
				player.addChatMessage(new ChatComponentText("Hit " + Keyboard.getKeyName(ClientProxy.useSpecial.getKeyCode()) + " to use your Secondary attack!"));
			}
		}
		return false;
	}

	@Override
	public void moveEntityWithHeading(float strafe, float forward)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
		{
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;

			if (this.riddenByEntity instanceof EntityPlayer)
			{
				strafe = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
				forward = ((EntityLivingBase)this.riddenByEntity).moveForward;

				if (forward <= 0.0F) forward *= 0.25F;
			}
			
			this.stepHeight = this.getRiddenStepHeight();
			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed(this.getAIRiddenMoveSpeed());
				super.moveEntityWithHeading(strafe, forward);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f2 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
			if (f2 > 1.0F)  f2 = 1.0F;

			this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(strafe, forward);
		}
	}

	public float getRiddenStepHeight() {
		return 1.0F;
	}

	public float getAIRiddenMoveSpeed() {
		return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
	}
}
