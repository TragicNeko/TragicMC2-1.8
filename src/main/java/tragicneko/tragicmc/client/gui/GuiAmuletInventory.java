package tragicneko.tragicmc.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import tragicneko.tragicmc.TragicConfig;
import tragicneko.tragicmc.inventory.ContainerAmulet;
import tragicneko.tragicmc.inventory.InventoryAmulet;
import tragicneko.tragicmc.properties.PropertyAmulets;
import tragicneko.tragicmc.proxy.ClientProxy;

@SideOnly(Side.CLIENT)
public class GuiAmuletInventory extends GuiContainer
{
	private static final ResourceLocation iconLocation = new ResourceLocation("tragicmc:textures/gui/amulet_tentacles.png");
	private static final ResourceLocation iconLocation2 = new ResourceLocation("tragicmc:textures/gui/amulet_pinkstars.png");
	private static final ResourceLocation iconLocation3 = new ResourceLocation("tragicmc:textures/gui/amulet_tragicneko.png");
	private static final ResourceLocation iconLocation4 = new ResourceLocation("tragicmc:textures/gui/amulet_pokemon.png");
	private final InventoryAmulet inventory;

	private final PropertyAmulets amulets;

	public GuiAmuletInventory(EntityPlayer player, InventoryPlayer invPlayer, InventoryAmulet inventoryCustom)
	{
		super(new ContainerAmulet(player, invPlayer, inventoryCustom));
		this.inventory = inventoryCustom;
		this.amulets = PropertyAmulets.get(player);
	}

	@Override
	protected void keyTyped(char c, int keyCode) throws IOException {
		super.keyTyped(c, keyCode);
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E || keyCode == ClientProxy.openAmuletGui.getKeyCode()) {
			mc.thePlayer.closeScreen();
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(this.inventory.getName(), xSize - fontRendererObj.getStringWidth(this.inventory.getName()) - 40, -12, 0xAAAAAA);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		mc.renderEngine.bindTexture(iconLocation);
		drawTexturedModalRect(guiLeft + 16, guiTop - 2, 0, 0, xSize, ySize + 24);
	}
}
