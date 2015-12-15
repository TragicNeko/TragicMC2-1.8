package tragicneko.tragicmc.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tragicneko.tragicmc.TragicMC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCelledLamp extends Block {

	private String[] subNames = new String[] {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "DarkGray", "Pink", "Lime", "Yellow", "LightBlue",
			"Magenta", "Orange", "White"};
	private IIcon[] iconArray = new IIcon[subNames.length];

	public BlockCelledLamp() {
		super(Material.circuits);
		this.setCreativeTab(TragicMC.Survival);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(30.0F);
		this.setHardness(7.6F);
		this.setLightLevel(0.8F);
		this.setStepSound(soundTypeMetal);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= this.iconArray.length)
		{
			meta = this.iconArray.length - 1;
		}
		return this.iconArray[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		for (int i = 0; i < this.subNames.length; i++)
		{
			this.iconArray[i] = par1IconRegister.registerIcon("tragicmc:WrappedLamp" + this.subNames[i]);
		}
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2, List par3)
	{
		for (int i = 0; i < this.subNames.length; i++)
		{
			par3.add(new ItemStack(par1, 1, i));
		}
	}

}