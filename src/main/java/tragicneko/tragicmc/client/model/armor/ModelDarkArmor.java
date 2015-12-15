package tragicneko.tragicmc.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

/**
 * ModelDarkArmor - TragicNeko
 * Created using Tabula 4.1.1
 */
public class ModelDarkArmor extends ModelBiped {

	public ModelRenderer shape57;
	public ModelRenderer shape58;
	public ModelRenderer shape59;
	public ModelRenderer shape60;
	public ModelRenderer shape61;
	public ModelRenderer shape9;
	public ModelRenderer shape9_1;
	public ModelRenderer shape11;
	public ModelRenderer shape11_1;
	public ModelRenderer shape13;
	public ModelRenderer shape14;
	public ModelRenderer shape15;
	public ModelRenderer shape16;
	public ModelRenderer shape17;
	public ModelRenderer shape18;
	public ModelRenderer shape19;
	public ModelRenderer shape20;
	public ModelRenderer shape21;
	public ModelRenderer shape22;
	public ModelRenderer shape23;
	public ModelRenderer shape24;
	public ModelRenderer shape25;
	public ModelRenderer shape26;
	public ModelRenderer shape27;
	public ModelRenderer shape28;
	public ModelRenderer shape29;
	public ModelRenderer shape30;
	public ModelRenderer shape52;
	public ModelRenderer shape53;
	public ModelRenderer shape54;
	public ModelRenderer shape55;
	public ModelRenderer shape56;
	public ModelRenderer shape31;
	public ModelRenderer shape32;
	public ModelRenderer shape33;
	public ModelRenderer shape34;
	public ModelRenderer shape35;
	public ModelRenderer shape36;
	public ModelRenderer shape37;
	public ModelRenderer shape38;
	public ModelRenderer shape39;
	public ModelRenderer shape40;
	public ModelRenderer shape41;
	public ModelRenderer shape42;
	public ModelRenderer shape43;
	public ModelRenderer shape44;
	public ModelRenderer shape45;
	public ModelRenderer shape46;
	public ModelRenderer shape47;
	public ModelRenderer shape48;
	public ModelRenderer shape49;
	public ModelRenderer shape50;
	public ModelRenderer shape51;
	public ModelRenderer shape62;
	public ModelRenderer shape64;
	public ModelRenderer shape66;
	public ModelRenderer shape67;
	public ModelRenderer shape70;
	public ModelRenderer shape72;
	public ModelRenderer shape73;
	public ModelRenderer shape63;
	public ModelRenderer shape65;
	public ModelRenderer shape68;
	public ModelRenderer shape69;
	public ModelRenderer shape71;
	public ModelRenderer shape74;
	public ModelRenderer shape75;

	public final int armorType;

	public ModelDarkArmor(int i) {
		this.armorType = i;
		this.textureWidth = 96;
		this.textureHeight = 32;

		this.shape71 = new ModelRenderer(this, 48, 16);
		this.shape71.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape71.addBox(-0.9F, 9.5F, -4.0F, 4, 3, 1, 0.0F);
		this.shape18 = new ModelRenderer(this, 48, 16);
		this.shape18.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape18.addBox(-3.0F, -10.5F, -2.0F, 1, 1, 3, 0.0F);
		this.shape69 = new ModelRenderer(this, 48, 16);
		this.shape69.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape69.addBox(1.9F, 5.0F, -3.5F, 1, 5, 1, 0.0F);
		this.shape61 = new ModelRenderer(this, 48, 16);
		this.shape61.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape61.addBox(-7.0F, -3.5F, -2.0F, 5, 1, 4, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0, 0.0F);
		this.shape48 = new ModelRenderer(this, 48, 16);
		this.shape48.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape48.addBox(-4.0F, 3.0F, 3.0F, 8, 1, 1, 0.0F);
		this.shape14 = new ModelRenderer(this, 48, 16);
		this.shape14.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape14.addBox(2.0F, -10.5F, -6.0F, 1, 3, 1, 0.0F);
		this.shape16 = new ModelRenderer(this, 48, 16);
		this.shape16.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape16.addBox(3.0F, -10.5F, -6.0F, 1, 1, 5, 0.0F);
		this.shape19 = new ModelRenderer(this, 48, 16);
		this.shape19.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape19.addBox(2.0F, -10.5F, -2.0F, 1, 1, 3, 0.0F);
		this.shape68 = new ModelRenderer(this, 48, 16);
		this.shape68.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape68.addBox(0.9F, -0.5F, -3.5F, 1, 6, 1, 0.0F);
		this.shape51 = new ModelRenderer(this, 48, 16);
		this.shape51.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape51.addBox(-2.0F, 9.0F, 3.0F, 4, 1, 1, 0.0F);
		this.shape43 = new ModelRenderer(this, 48, 16);
		this.shape43.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape43.addBox(-4.5F, 8.5F, -4.0F, 1, 3, 1, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 0, 16);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.addBox(-6.0F, -2.5F, -3.0F, 6, 6, 6, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		if (this.armorType == 2) this.bipedLeftLeg.addBox(-2.0F, -0.5F, -2.5F, 5, 12, 5, 0.0F);
		this.shape58 = new ModelRenderer(this, 64, 0);
		this.shape58.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape58.addBox(-5.0F, -1.0F, -4.0F, 4, 4, 1, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 0, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.addBox(0.0F, -2.5F, -3.0F, 6, 6, 6, 0.0F);
		this.shape9 = new ModelRenderer(this, 48, 0);
		this.shape9.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape9.addBox(4.5F, -6.0F, 0.0F, 2, 2, 2, 0.0F);
		this.shape39 = new ModelRenderer(this, 48, 16);
		this.shape39.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape39.addBox(-1.0F, 4.5F, -4.0F, 2, 7, 1, 0.0F);
		this.shape59 = new ModelRenderer(this, 64, 0);
		this.shape59.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape59.addBox(-5.0F, -1.0F, 3.0F, 4, 4, 1, 0.0F);
		this.shape40 = new ModelRenderer(this, 48, 16);
		this.shape40.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape40.addBox(2.5F, 5.0F, -4.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(shape40, 0.0F, 0.0F, 0.017453292519943295F);
		this.shape35 = new ModelRenderer(this, 48, 16);
		this.shape35.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape35.addBox(1.5F, 1.5F, -4.0F, 1, 4, 1, 0.0F);
		this.shape28 = new ModelRenderer(this, 0, 0);
		this.shape28.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape28.addBox(-4.5F, -8.5F, -4.0F, 1, 5, 8, 0.0F);
		this.shape41 = new ModelRenderer(this, 48, 16);
		this.shape41.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape41.addBox(-3.5F, 5.0F, -4.0F, 1, 4, 1, 0.0F);
		this.shape36 = new ModelRenderer(this, 48, 16);
		this.shape36.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape36.addBox(3.5F, -1.0F, -4.0F, 3, 2, 1, 0.0F);
		this.shape46 = new ModelRenderer(this, 48, 16);
		this.shape46.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape46.addBox(-1.0F, -1.0F, 3.0F, 2, 13, 2, 0.0F);
		this.shape72 = new ModelRenderer(this, 48, 16);
		this.shape72.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape72.addBox(0.1F, -0.5F, 2.5F, 1, 4, 1, 0.0F);
		this.shape13 = new ModelRenderer(this, 64, 0);
		this.shape13.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape13.addBox(-2.0F, -8.0F, -7.0F, 4, 3, 2, 0.0F);
		this.shape23 = new ModelRenderer(this, 48, 16);
		this.shape23.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape23.addBox(1.0F, -10.5F, 5.0F, 1, 3, 1, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody.addBox(-5.0F, -0.5F, -3.0F, 10, 12, 6, 0.0F);
		this.shape38 = new ModelRenderer(this, 48, 16);
		this.shape38.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape38.addBox(-1.0F, -0.5F, -4.0F, 2, 2, 1, 0.0F);
		this.bipedEars = new ModelRenderer(this, 24, 0);
		this.bipedEars.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedEars.addBox(-3.0F, -6.0F, -1.0F, 0, 0, 0, 0.0F);
		this.shape62 = new ModelRenderer(this, 0, 0);
		this.shape62.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape62.addBox(-3.9F, 8.5F, -3.0F, 6, 4, 6, 0.0F);
		this.shape30 = new ModelRenderer(this, 0, 0);
		this.shape30.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape30.addBox(-4.5F, -8.5F, 4.0F, 9, 5, 1, 0.0F);
		this.shape56 = new ModelRenderer(this, 48, 16);
		this.shape56.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape56.addBox(2.0F, -3.5F, -2.0F, 5, 1, 4, 0.0F);
		this.shape57 = new ModelRenderer(this, 64, 0);
		this.shape57.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape57.addBox(-7.0F, -1.0F, -2.0F, 1, 4, 4, 0.0F);
		this.shape11_1 = new ModelRenderer(this, 48, 0);
		this.shape11_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape11_1.addBox(-8.0F, -11.0F, 0.5F, 2, 5, 1, 0.0F);
		this.shape15 = new ModelRenderer(this, 48, 16);
		this.shape15.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape15.addBox(-3.0F, -10.5F, -6.0F, 1, 3, 1, 0.0F);
		this.shape22 = new ModelRenderer(this, 48, 16);
		this.shape22.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape22.addBox(-1.5F, -10.5F, 4.0F, 3, 1, 1, 0.0F);
		this.shape32 = new ModelRenderer(this, 48, 16);
		this.shape32.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape32.addBox(2.5F, 0.0F, -4.0F, 1, 2, 1, 0.0F);
		this.shape65 = new ModelRenderer(this, 48, 0);
		this.shape65.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape65.addBox(-0.1F, 6.5F, -3.5F, 2, 2, 1, 0.0F);
		this.shape42 = new ModelRenderer(this, 48, 16);
		this.shape42.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape42.addBox(3.5F, 8.5F, -4.0F, 1, 3, 1, 0.0F);
		this.shape11 = new ModelRenderer(this, 48, 0);
		this.shape11.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape11.addBox(6.0F, -11.0F, 0.5F, 2, 5, 1, 0.0F);
		this.shape45 = new ModelRenderer(this, 64, 0);
		this.shape45.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape45.addBox(5.0F, 6.0F, -2.0F, 1, 5, 4, 0.0F);
		this.shape26 = new ModelRenderer(this, 48, 16);
		this.shape26.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape26.addBox(-3.0F, -8.5F, 5.0F, 1, 5, 1, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHeadwear.addBox(-4.5F, -9.5F, -5.0F, 9, 4, 1, 0.0F);
		this.shape60 = new ModelRenderer(this, 0, 0);
		this.shape60.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape60.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape70 = new ModelRenderer(this, 48, 16);
		this.shape70.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape70.addBox(-2.9F, 9.5F, -4.0F, 4, 3, 1, 0.0F);
		this.shape29 = new ModelRenderer(this, 0, 0);
		this.shape29.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape29.addBox(3.5F, -8.5F, -4.0F, 1, 5, 8, 0.0F);
		this.shape53 = new ModelRenderer(this, 64, 0);
		this.shape53.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape53.addBox(1.0F, -1.0F, -4.0F, 4, 4, 1, 0.0F);
		this.shape50 = new ModelRenderer(this, 48, 16);
		this.shape50.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape50.addBox(-2.0F, 7.0F, 3.0F, 4, 1, 1, 0.0F);
		this.shape74 = new ModelRenderer(this, 48, 16);
		this.shape74.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape74.addBox(-0.9F, -0.5F, 2.5F, 1, 4, 1, 0.0F);
		this.shape21 = new ModelRenderer(this, 48, 16);
		this.shape21.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape21.addBox(-0.5F, -10.5F, 1.0F, 1, 1, 3, 0.0F);
		this.shape33 = new ModelRenderer(this, 48, 16);
		this.shape33.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape33.addBox(-3.5F, 0.0F, -4.0F, 1, 2, 1, 0.0F);
		this.shape27 = new ModelRenderer(this, 0, 0);
		this.shape27.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape27.addBox(-4.5F, -9.5F, -4.0F, 9, 1, 9, 0.0F);
		this.shape31 = new ModelRenderer(this, 64, 0);
		this.shape31.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape31.addBox(-1.5F, 1.5F, -5.0F, 3, 3, 2, 0.0F);
		this.shape49 = new ModelRenderer(this, 48, 16);
		this.shape49.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape49.addBox(-3.0F, 5.0F, 3.0F, 6, 1, 1, 0.0F);
		this.shape52 = new ModelRenderer(this, 64, 0);
		this.shape52.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape52.addBox(6.0F, -1.0F, -2.0F, 1, 4, 4, 0.0F);
		this.setRotateAngle(shape52, 0.0F, 0.0F, 0.017453292519943295F);
		this.shape63 = new ModelRenderer(this, 0, 0);
		this.shape63.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape63.addBox(-1.9F, 8.5F, -3.0F, 6, 4, 6, 0.0F);
		this.shape20 = new ModelRenderer(this, 48, 16);
		this.shape20.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape20.addBox(-2.0F, -10.5F, 0.0F, 4, 1, 1, 0.0F);
		this.shape73 = new ModelRenderer(this, 48, 16);
		this.shape73.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape73.addBox(-0.9F, 3.0F, 2.5F, 1, 6, 1, 0.0F);
		this.shape37 = new ModelRenderer(this, 48, 16);
		this.shape37.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape37.addBox(-6.5F, -1.0F, -4.0F, 3, 2, 1, 0.0F);
		this.shape47 = new ModelRenderer(this, 48, 16);
		this.shape47.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape47.addBox(-2.0F, 1.0F, 3.0F, 4, 1, 1, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		if (this.armorType == 2) this.bipedRightLeg.addBox(-3.0F, -0.5F, -2.5F, 5, 12, 5, 0.0F);
		this.shape64 = new ModelRenderer(this, 48, 0);
		this.shape64.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape64.addBox(-1.9F, 6.5F, -3.5F, 2, 2, 1, 0.0F);
		this.shape9_1 = new ModelRenderer(this, 48, 0);
		this.shape9_1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape9_1.addBox(-6.5F, -6.0F, 0.0F, 2, 2, 2, 0.0F);
		this.shape44 = new ModelRenderer(this, 64, 0);
		this.shape44.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape44.addBox(-6.0F, 6.0F, -2.0F, 1, 5, 4, 0.0F);
		this.shape66 = new ModelRenderer(this, 48, 16);
		this.shape66.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape66.addBox(-1.9F, -0.5F, -3.5F, 1, 6, 1, 0.0F);
		this.shape17 = new ModelRenderer(this, 48, 16);
		this.shape17.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape17.addBox(-4.0F, -10.5F, -6.0F, 1, 1, 5, 0.0F);
		this.shape67 = new ModelRenderer(this, 48, 16);
		this.shape67.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape67.addBox(-2.9F, 5.0F, -3.5F, 1, 5, 1, 0.0F);
		this.shape75 = new ModelRenderer(this, 48, 16);
		this.shape75.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape75.addBox(0.1F, 3.0F, 2.5F, 1, 6, 1, 0.0F);
		this.shape24 = new ModelRenderer(this, 48, 16);
		this.shape24.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape24.addBox(-2.0F, -10.5F, 5.0F, 1, 3, 1, 0.0F);
		this.shape54 = new ModelRenderer(this, 64, 0);
		this.shape54.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape54.addBox(1.0F, -1.0F, 3.0F, 4, 4, 1, 0.0F);
		this.shape34 = new ModelRenderer(this, 48, 16);
		this.shape34.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape34.addBox(-2.5F, 1.5F, -4.0F, 1, 4, 1, 0.0F);
		this.shape55 = new ModelRenderer(this, 0, 0);
		this.shape55.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape55.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.shape25 = new ModelRenderer(this, 48, 16);
		this.shape25.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.shape25.addBox(2.0F, -8.5F, 5.0F, 1, 5, 1, 0.0F);
		if (this.armorType == 3) this.bipedLeftLeg.addChild(this.shape71);
		this.bipedHeadwear.addChild(this.shape18);
		if (this.armorType == 2) this.bipedLeftLeg.addChild(this.shape69);
		this.bipedRightArm.addChild(this.shape61);
		this.bipedBody.addChild(this.shape48);
		this.bipedHeadwear.addChild(this.shape14);
		this.bipedHeadwear.addChild(this.shape16);
		this.bipedHeadwear.addChild(this.shape19);
		if (this.armorType == 2) this.bipedLeftLeg.addChild(this.shape68);
		this.bipedBody.addChild(this.shape51);
		this.bipedBody.addChild(this.shape43);
		this.bipedRightArm.addChild(this.shape58);
		this.bipedHeadwear.addChild(this.shape9);
		this.bipedBody.addChild(this.shape39);
		this.bipedRightArm.addChild(this.shape59);
		this.bipedBody.addChild(this.shape40);
		this.bipedBody.addChild(this.shape35);
		this.bipedHeadwear.addChild(this.shape28);
		this.bipedBody.addChild(this.shape41);
		this.bipedBody.addChild(this.shape36);
		this.bipedBody.addChild(this.shape46);
		if (this.armorType == 2) this.bipedRightLeg.addChild(this.shape72);
		this.bipedHeadwear.addChild(this.shape13);
		this.bipedHeadwear.addChild(this.shape23);
		this.bipedBody.addChild(this.shape38);
		if (this.armorType == 3) this.bipedRightLeg.addChild(this.shape62);
		this.bipedHeadwear.addChild(this.shape30);
		this.bipedLeftArm.addChild(this.shape56);
		this.bipedRightArm.addChild(this.shape57);
		this.bipedHeadwear.addChild(this.shape11_1);
		this.bipedHeadwear.addChild(this.shape15);
		this.bipedHeadwear.addChild(this.shape22);
		this.bipedBody.addChild(this.shape32);
		if (this.armorType == 3) this.bipedLeftLeg.addChild(this.shape65);
		this.bipedBody.addChild(this.shape42);
		this.bipedHeadwear.addChild(this.shape11);
		this.bipedBody.addChild(this.shape45);
		this.bipedHeadwear.addChild(this.shape26);
		this.bipedRightArm.addChild(this.shape60);
		if (this.armorType == 3) this.bipedRightLeg.addChild(this.shape70);
		this.bipedHeadwear.addChild(this.shape29);
		this.bipedLeftArm.addChild(this.shape53);
		this.bipedBody.addChild(this.shape50);
		if (this.armorType == 2) this.bipedLeftLeg.addChild(this.shape74);
		this.bipedHeadwear.addChild(this.shape21);
		this.bipedBody.addChild(this.shape33);
		this.bipedHeadwear.addChild(this.shape27);
		this.bipedBody.addChild(this.shape31);
		this.bipedBody.addChild(this.shape49);
		this.bipedLeftArm.addChild(this.shape52);
		if (this.armorType == 3) this.bipedLeftLeg.addChild(this.shape63);
		this.bipedHeadwear.addChild(this.shape20);
		if (this.armorType == 2) this.bipedRightLeg.addChild(this.shape73);
		this.bipedBody.addChild(this.shape37);
		this.bipedBody.addChild(this.shape47);
		if (this.armorType == 3) this.bipedRightLeg.addChild(this.shape64);
		this.bipedHeadwear.addChild(this.shape9_1);
		this.bipedBody.addChild(this.shape44);
		if (this.armorType == 2) this.bipedRightLeg.addChild(this.shape66);
		this.bipedHeadwear.addChild(this.shape17);
		if (this.armorType == 2) this.bipedRightLeg.addChild(this.shape67);
		if (this.armorType == 2) this.bipedLeftLeg.addChild(this.shape75);
		this.bipedHeadwear.addChild(this.shape24);
		this.bipedLeftArm.addChild(this.shape54);
		this.bipedBody.addChild(this.shape34);
		this.bipedLeftArm.addChild(this.shape55);
		this.bipedHeadwear.addChild(this.shape25);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

		this.isSneak = entity.isSneaking();
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		if (this.isChild)
		{
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
			GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
			if (this.armorType == 0) this.bipedHead.render(f5);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
			if (this.armorType == 1) this.bipedBody.render(f5);
			if (this.armorType == 1) this.bipedRightArm.render(f5);
			if (this.armorType == 1) this.bipedLeftArm.render(f5);
			if (this.armorType >= 2) this.bipedRightLeg.render(f5);
			if (this.armorType >= 2) this.bipedLeftLeg.render(f5);
			if (this.armorType == 0) this.bipedHeadwear.render(f5);
			GL11.glPopMatrix();
		}
		else
		{
			if (this.armorType == 0) this.bipedHead.render(f5);
			if (this.armorType == 1) this.bipedBody.render(f5);
			if (this.armorType == 1) this.bipedRightArm.render(f5);
			if (this.armorType == 1) this.bipedLeftArm.render(f5);
			if (this.armorType >= 2) this.bipedRightLeg.render(f5);
			if (this.armorType >= 2) this.bipedLeftLeg.render(f5);
			if (this.armorType == 0) this.bipedHeadwear.render(f5);
		}
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}