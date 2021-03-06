package tragicneko.tragicmc;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class TragicPotion extends Potion {

	public static Potion Corruption, Disorientation, Stun, Fear, Cripple, Malnourish, Submission, Inhibit, LeadFoot, Hacked, Burned; //Frozen, Deafening, Nightmare, Exasperate, EvilPresence, Bleed;
	public static Potion Flight, AquaSuperiority, Immunity, Resurrection, Harmony, Invulnerability, Clarity, Convergence, Divinity;

	private ItemStack stackIcon;

	public TragicPotion(ResourceLocation rl, boolean par2, int par3) {
		super(rl, par2, par3);
	}

	public void setIcon(ItemStack icon)
	{
		this.stackIcon = icon;
	}

	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		x += 6;
		y += 7;
		net.minecraft.client.renderer.entity.RenderItem itemRender = mc.getRenderItem();
		if (this.stackIcon == null) this.stackIcon = new ItemStack(Items.apple);
		net.minecraft.client.renderer.GlStateManager.enableRescaleNormal();
		net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
		itemRender.renderItemIntoGUI(this.stackIcon, x, y);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		net.minecraft.client.renderer.GlStateManager.disableRescaleNormal();
	}

	public static void load()
	{
		if (TragicConfig.getBoolean("allowCorruption")) Corruption = (new TragicPotion(new ResourceLocation("tragicmc:corruption"), true, 0x000000).setPotionName("potion.corruption"));
		if (TragicConfig.getBoolean("allowDisorientation")) Disorientation = (new TragicPotion(new ResourceLocation("tragicmc:disorientation"), true, 0x521A55).setPotionName("potion.disorientation"));
		if (TragicConfig.getBoolean("allowStun")) Stun = (new TragicPotion(new ResourceLocation("tragicmc:stun"), true, 0xC2DC7D).setPotionName("potion.stun").registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "d35fe2b0-2aca-4d5e-b3e3-3fe041dbaf15", -3.0D, 2));
		if (TragicConfig.getBoolean("allowFear")) Fear = (new TragicPotion(new ResourceLocation("tragicmc:fear"), true, 0x4A4A5F).setPotionName("potion.fear"));
		if (TragicConfig.getBoolean("allowCripple")) Cripple = (new TragicPotion(new ResourceLocation("tragicmc:cripple"), true, 0x640000).setPotionName("potion.cripple").registerPotionAttributeModifier(SharedMonsterAttributes.maxHealth, "04df3109-9bca-4517-8855-8064742028e4", -2.0D, 0));
		if (TragicConfig.getBoolean("allowMalnourish")) Malnourish = (new TragicPotion(new ResourceLocation("tragicmc:malnourish"), true, 0x59701C).setPotionName("potion.malnourish"));
		if (TragicConfig.getBoolean("allowSubmission")) Submission = (new TragicPotion(new ResourceLocation("tragicmc:submission"), true, 0xB152B7).setPotionName("potion.submission"));
		if (TragicConfig.getBoolean("allowInhibit")) Inhibit = (new TragicPotion(new ResourceLocation("tragicmc:inhibit"), true, 0x232323).setPotionName("potion.inhibit"));
		if (TragicConfig.getBoolean("allowLeadFoot")) LeadFoot = (new TragicPotion(new ResourceLocation("tragicmc:leadFoot"), true, 0x78AB76).setPotionName("potion.leadFoot"));
		if (TragicConfig.getBoolean("allowHacked")) Hacked = (new TragicPotion(new ResourceLocation("tragicmc:hacked"), true, 0xC6F6FF).setPotionName("potion.hacked"));
		if (TragicConfig.getBoolean("allowBurned")) Burned = (new TragicPotion(new ResourceLocation("tragicmc:burned"), true, 0xFF0000).setPotionName("potion.burned"));
		//Deafening = new TragicPotion(TragicConfig.idHacked + 1, true, 0x000000).setPotionName("potion.deafening");
		//Nightmare = new TragicPotion(TragicConfig.idHacked + 2, true, 0x000000).setPotionName("potion.nightmare");
		//Exasperate = new TragicPotion(TragicConfig.idHacked + 3, true, 0x000000).setPotionName("potion.exasperate");
		//EvilPresence = new TragicPotion(TragicConfig.idHacked + 4, true, 0x000000).setPotionName("potion.evilPresence");
		//Frozen = new TragicPotion(72, true, 0x000000).setPotionName("potion.frozen").func_111184_a(SharedMonsterAttributes.movementSpeed, "d35fe2b0-2aca-4d5e-b3e3-3fe041dbaf15", -3.0D, 2);
		/*Bleed = new TragicPotion(TragicConfig.idHacked + 5, true, 0x000000) {
		@Override
		public void performEffect(EntityLivingBase entity, int amp)
		{
			float bleedOut = 0F;
			PropertyMisc misc = PropertyMisc.get(entity);
			if (misc != null) bleedOut = misc.bleedOutTime / 120F;
			entity.attackEntityFrom(DamageHelper.bleed, 1.0F + bleedOut);
			TragicMC.logInfo("Bleed out time is " + misc.bleedOutTime + ", damage is " + (bleedOut + 1.0F));
		}
		
		@Override
		public boolean isReady(int dur, int amp)
		{
			int k = 40 >> MathHelper.floor_double(amp / 2);
			return k > 0 ? dur % k == 0 : true;
		}
		}.setPotionName("potion.bleed"); */

		if (TragicConfig.getBoolean("allowFlight")) Flight = (new TragicPotion(new ResourceLocation("tragicmc:flight"), false, 0xFDDC69).setPotionName("potion.flight"));
		if (TragicConfig.getBoolean("allowAquaSuperiority")) AquaSuperiority = (new TragicPotion(new ResourceLocation("tragicmc:aquaSuperiority"), true, 0x69B9FD).setPotionName("potion.aquaSuperiority"));
		if (TragicConfig.getBoolean("allowImmunity")) Immunity = (new TragicPotion(new ResourceLocation("tragicmc:immunity"), false, 0xBABABA).setPotionName("potion.immunity"));
		if (TragicConfig.getBoolean("allowResurrection")) Resurrection = (new TragicPotion(new ResourceLocation("tragicmc:resurrection"), false, 0x9DFD69).setPotionName("potion.resurrection"));
		if (TragicConfig.getBoolean("allowHarmony")) Harmony = (new TragicPotion(new ResourceLocation("tragicmc:harmony"), false, 0xE67CED).setPotionName("potion.harmony").registerPotionAttributeModifier(SharedMonsterAttributes.followRange, "ac2b9b0e-2eb2-443f-8ca2-a045f36f9e73", -256.0, 2));
		if (TragicConfig.getBoolean("allowInvulnerability")) Invulnerability = (new TragicPotion(new ResourceLocation("tragicmc:invulnerability"), false, 0xD5ECFF).setPotionName("potion.invulnerability").registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "43d74ab6-d058-40e4-9761-70b0b80b8743", -0.2D, 2));
		if (TragicConfig.getBoolean("allowClarity")) Clarity = (new TragicPotion(new ResourceLocation("tragicmc:clarity"), false, 0xFFFFFF).setPotionName("potion.clarity"));
		if (TragicConfig.getBoolean("allowConvergence")) Convergence = (new TragicPotion(new ResourceLocation("tragicmc:convergence"), false, 0x9B2525).setPotionName("potion.convergence"));
		if (TragicConfig.getBoolean("allowDivinity")) Divinity = (new TragicPotion(new ResourceLocation("tragicmc:divinity"), false, 0xFFFFFF).setPotionName("potion.divinity"));
	}

	public static void setPotionIcons()
	{
		if (Corruption != null) ((TragicPotion) Corruption).setIcon(new ItemStack(TragicItems.DarkParticles));
		if (Disorientation != null) ((TragicPotion) Disorientation).setIcon(new ItemStack(Items.dye));
		if (Stun != null) ((TragicPotion) Stun).setIcon(new ItemStack(TragicItems.LightParticles));
		if (Fear != null) ((TragicPotion) Fear).setIcon(new ItemStack(Blocks.pumpkin, 1));
		if (Cripple != null) ((TragicPotion) Cripple).setIcon(new ItemStack(Items.bone));
		if (Malnourish != null) ((TragicPotion) Malnourish).setIcon(new ItemStack(TragicItems.NourishmentSacrifice));
		if (Submission != null) ((TragicPotion) Submission).setIcon(new ItemStack(TragicItems.Thorns));
		if (Inhibit != null) ((TragicPotion) Inhibit).setIcon(new ItemStack(TragicItems.Ash));
		if (LeadFoot != null) ((TragicPotion) LeadFoot).setIcon(new ItemStack(Blocks.anvil));
		if (Hacked != null) ((TragicPotion) Hacked).setIcon(new ItemStack(TragicBlocks.CircuitBlock));
		if (Burned != null) ((TragicPotion) Burned).setIcon(new ItemStack(TragicBlocks.SmoothNetherrack));
		//((TragicPotion) Deafening).setIcon(new ItemStack(Items.record_11));
		//((TragicPotion) Nightmare).setIcon(new ItemStack(TragicItems.CorruptedEgg));
		//((TragicPotion) Exasperate).setIcon(new ItemStack(TragicItems.EnchantedTears));
		//((TragicPotion) EvilPresence).setIcon(new ItemStack(TragicItems.PureDarkness));
		//((TragicPotion) Bleed).setIcon(new ItemStack(TragicItems.BloodSacrifice));
		//if (Frozen != null) ((TragicPotion) Frozen).setIcon(new ItemStack(TragicItems.IceOrb));

		if (Flight != null) ((TragicPotion) Flight).setIcon(new ItemStack(Items.feather));
		if (AquaSuperiority != null) ((TragicPotion) AquaSuperiority).setIcon(new ItemStack(TragicItems.EnchantedTears));
		if (Immunity != null) ((TragicPotion) Immunity).setIcon(new ItemStack(Items.milk_bucket));
		if (Resurrection != null) ((TragicPotion) Resurrection).setIcon(new ItemStack(TragicItems.BoneMarrow));
		if (Harmony != null) ((TragicPotion) Harmony).setIcon(new ItemStack(TragicItems.Sushi));
		if (Invulnerability != null) ((TragicPotion) Invulnerability).setIcon(new ItemStack(TragicItems.AwakeningStone));
		if (Clarity != null) ((TragicPotion) Clarity).setIcon(new ItemStack(Items.ender_eye));
		if (Convergence != null) ((TragicPotion) Convergence).setIcon(new ItemStack(TragicItems.DoomConsume));
		if (Divinity != null) ((TragicPotion) Divinity).setIcon(new ItemStack(TragicItems.SynapseLink));
	}
}
