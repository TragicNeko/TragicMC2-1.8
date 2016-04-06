package tragicneko.tragicmc.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tragicneko.tragicmc.worldgen.structure.Structure;

public class ItemBlockStructureSeeds2 extends TragicItemBlock {

	public ItemBlockStructureSeeds2(Block p_i45326_1_) {
		super(p_i45326_1_, null);
		this.setUnlocalizedName("tragicmc.structureSeed");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		int damage = par1ItemStack.getItemDamage() + 16;
		return damage < Structure.structureList.length && Structure.structureList[damage] != null ? Structure.structureList[damage].getStructureColor() : 0;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int damage = itemstack.getItemDamage() + 16;

		if (damage >= Structure.structureList.length) damage = Structure.structureList.length - 1;
		
		if (Structure.structureList[damage] == null) damage = 0;
		return getUnlocalizedName() + "." + Structure.structureList[damage].getUnlocalizedName();
	}
}
