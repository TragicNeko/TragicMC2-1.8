package tragicneko.tragicmc.proxy;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import tragicneko.tragicmc.TragicAchievements;
import tragicneko.tragicmc.TragicBiome;
import tragicneko.tragicmc.TragicBlocks;
import tragicneko.tragicmc.TragicConfig;
import tragicneko.tragicmc.TragicEnchantments;
import tragicneko.tragicmc.TragicEntities;
import tragicneko.tragicmc.TragicItems;
import tragicneko.tragicmc.TragicMC;
import tragicneko.tragicmc.TragicPotion;
import tragicneko.tragicmc.TragicRecipes;
import tragicneko.tragicmc.client.gui.GuiAmuletInventory;
import tragicneko.tragicmc.doomsday.DoomsdayManager;
import tragicneko.tragicmc.events.AchievementEvents;
import tragicneko.tragicmc.events.AmuletEvents;
import tragicneko.tragicmc.events.ChallengeItemEvents;
import tragicneko.tragicmc.events.DoomEvents;
import tragicneko.tragicmc.events.DropEvents;
import tragicneko.tragicmc.events.EnchantmentEvents;
import tragicneko.tragicmc.events.MiscEvents;
import tragicneko.tragicmc.events.PotionEvents;
import tragicneko.tragicmc.events.RespawnDoomEvents;
import tragicneko.tragicmc.events.ServerTickEvents;
import tragicneko.tragicmc.events.VanillaChangingEvents;
import tragicneko.tragicmc.inventory.ContainerAmulet;
import tragicneko.tragicmc.network.MessageAmulet;
import tragicneko.tragicmc.network.MessageAttack;
import tragicneko.tragicmc.network.MessageDoom;
import tragicneko.tragicmc.network.MessageFlight;
import tragicneko.tragicmc.network.MessageFrozenInput;
import tragicneko.tragicmc.network.MessageGui;
import tragicneko.tragicmc.network.MessageHandlerAmulet;
import tragicneko.tragicmc.network.MessageHandlerAttack;
import tragicneko.tragicmc.network.MessageHandlerDoom;
import tragicneko.tragicmc.network.MessageHandlerFlight;
import tragicneko.tragicmc.network.MessageHandlerFrozenInput;
import tragicneko.tragicmc.network.MessageHandlerGui;
import tragicneko.tragicmc.network.MessageHandlerSpawnParticle;
import tragicneko.tragicmc.network.MessageHandlerUseDoomsday;
import tragicneko.tragicmc.network.MessageParticle;
import tragicneko.tragicmc.network.MessageUseDoomsday;
import tragicneko.tragicmc.properties.PropertyAmulets;
import tragicneko.tragicmc.worldgen.FlowerWorldGen;

public class CommonProxy implements IGuiHandler {

	public static SimpleNetworkWrapper net;
	private static final Logger logger = LogManager.getLogger(TragicMC.MODID);

	public static final int AMULET_GUI_ID = 0;

	public void init(FMLInitializationEvent event){

	}

	public void postInit(FMLPostInitializationEvent event) {
		/*//might try to implement this later on, for now it doesn't really work correctly
		if (TragicConfig.allowNonMobBlocks && TragicConfig.allowNonMobItems)
		{
			TragicMC.logInfo("Recreating stats for blocks, items and mobs...");
			try
			{
				recreateStatList();
			}
			catch (Exception e)
			{
				TragicMC.logError("Error caught while attempting to recreate stats", e);
			}
		} */
	}

	private static void recreateStatList() {
		for (Block block : Block.blockRegistry)
		{
			Item item = Item.getItemFromBlock(block);

			if (item != null)
			{
				int i = Block.getIdFromBlock(block);
				String s = getResourceFor(item);

				if (s != null && block.getEnableStats() && StatList.mineBlockStatArray[i] == null)
				{
					StatList.mineBlockStatArray[i] = (new StatCrafting("stat.mineBlock.", s, new ChatComponentTranslation("stat.mineBlock", new Object[] {(new ItemStack(block)).getChatComponent()}), item)).registerStat();
					StatList.objectMineStats.add((StatCrafting)StatList.mineBlockStatArray[i]);
				}
			}
		}

		for (Item item : Item.itemRegistry)
		{
			if (item != null)
			{
				int i = Item.getIdFromItem(item);
				String s = getResourceFor(item);

				if (s != null && StatList.objectUseStats[i] == null)
				{
					StatList.objectUseStats[i] = (new StatCrafting("stat.useItem.", s, new ChatComponentTranslation("stat.useItem", new Object[] {(new ItemStack(item)).getChatComponent()}), item)).registerStat();

					if (!(item instanceof ItemBlock))
					{
						StatList.itemStats.add((StatCrafting)StatList.objectUseStats[i]);
					}
				}
			}
		}

		for (Item item : Item.itemRegistry)
		{
			if (item != null)
			{
				int i = Item.getIdFromItem(item);
				String s = getResourceFor(item);

				if (s != null && item.isDamageable() && StatList.objectBreakStats[i] == null)
				{
					StatList.objectBreakStats[i] = (new StatCrafting("stat.breakItem.", s, new ChatComponentTranslation("stat.breakItem", new Object[] {(new ItemStack(item)).getChatComponent()}), item)).registerStat();
				}
			}
		}

		Set<Item> set = Sets.<Item>newHashSet();

		for (IRecipe irecipe : CraftingManager.getInstance().getRecipeList())
		{
			if (irecipe.getRecipeOutput() != null)
			{
				set.add(irecipe.getRecipeOutput().getItem());
			}
		}

		for (ItemStack itemstack : FurnaceRecipes.instance().getSmeltingList().values())
		{
			set.add(itemstack.getItem());
		}

		for (Item item : set)
		{
			if (item != null)
			{
				int i = Item.getIdFromItem(item);
				String s = getResourceFor(item);

				if (s != null && StatList.objectCraftStats[i] == null)
				{
					StatList.objectCraftStats[i] = (new StatCrafting("stat.craftItem.", s, new ChatComponentTranslation("stat.craftItem", new Object[] {(new ItemStack(item)).getChatComponent()}), item)).registerStat();
				}
			}
		}
	}

	private static String getResourceFor(Item p_180204_0_)
	{
		ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(p_180204_0_);
		return resourcelocation != null ? resourcelocation.toString().replace(':', '.') : null;
	}

	public void preInit(FMLPreInitializationEvent event) {
		TragicConfig.load(event);
		//TragicNewConfig.load(event); //parallel testing to be done after the rewrite is finished up

		if (TragicConfig.allowPotions)
		{
			TragicPotion.load();
			registerEvent(new PotionEvents());
		}

		if (TragicConfig.allowEnchantments) TragicEnchantments.load();
		if (TragicConfig.allowEnchantments) registerEvent(new EnchantmentEvents());

		if (TragicConfig.allowSurvivalTab)
		{
			TragicMC.Survival = (new CreativeTabs("tragicMCSurvival") {
				@Override
				public Item getTabIconItem() {
					return TragicItems.AwakeningStone;
				}
			});
		}

		TragicMC.Creative = (new CreativeTabs("tragicMCCreative") {
			@Override
			public Item getTabIconItem() {
				return TragicItems.NekoNekoWand;
			}
		});

		TragicBlocks.load();
		TragicItems.load();
		if (TragicConfig.allowRandomWeaponLore && TragicConfig.allowNonMobItems) tragicneko.tragicmc.util.LoreHelper.registerLoreJson(event.getModConfigurationDirectory());
		if (TragicConfig.allowPotions && TragicConfig.allowNonMobBlocks && TragicConfig.allowNonMobItems) TragicPotion.setPotionIcons();
		if (TragicConfig.allowRecipes) TragicRecipes.load();
		if (TragicConfig.allowAmulets) registerEvent(new AmuletEvents());

		registerEvent(new MiscEvents());

		if (TragicConfig.allowDoom)
		{
			registerEvent(new DoomEvents());
			registerEvent(new RespawnDoomEvents());
		}

		if (TragicConfig.allowDimension)
		{
			if (TragicConfig.allowCollision)
			{
				if (DimensionManager.isDimensionRegistered(TragicConfig.collisionID))
				{
					int id = DimensionManager.getNextFreeDimId();
					TragicConfig.collisionID = id;
					TragicConfig.collisionProviderID = id;
				}

				DimensionManager.registerProviderType(TragicConfig.collisionProviderID, tragicneko.tragicmc.dimension.TragicWorldProvider.class, TragicConfig.keepCollisionLoaded);
				DimensionManager.registerDimension(TragicConfig.collisionID, TragicConfig.collisionProviderID);
				TragicMC.logInfo("Dimension (The Collision) was registered with an ID of " + TragicConfig.collisionID);
			}

			if (TragicConfig.allowSynapse)
			{
				if (DimensionManager.isDimensionRegistered(TragicConfig.synapseID))
				{
					int id = DimensionManager.getNextFreeDimId();
					TragicConfig.synapseID = id;
					TragicConfig.synapseProviderID = id;
				}

				DimensionManager.registerProviderType(TragicConfig.synapseProviderID, tragicneko.tragicmc.dimension.SynapseWorldProvider.class, TragicConfig.keepSynapseLoaded);
				DimensionManager.registerDimension(TragicConfig.synapseID, TragicConfig.synapseProviderID);
				TragicMC.logInfo("Dimension (Synapse) was registered with an ID of " + TragicConfig.synapseID);
			}

			DimensionManager.registerProviderType(4, tragicneko.tragicmc.dimension.NekoHomeworldProvider.class, false);
			DimensionManager.registerDimension(4, 4);
			TragicMC.logInfo("Dimension (Neko Homeworld) was registered with an ID of " + 4);
			/* 
			DimensionManager.registerProviderType(5, tragicneko.tragicmc.dimension.WildsWorldProvider.class, false);
			DimensionManager.registerDimension(5, 5);
			TragicMC.logInfo("Dimension (The Wilds) was registered with an ID of " + 5); */

			TragicBiome.load();
			MinecraftForge.ORE_GEN_BUS.register(new tragicneko.tragicmc.events.MiscEvents());
		}

		TragicEntities.load();
		if (TragicConfig.allowMobs) tragicneko.tragicmc.util.EntityDropHelper.fill();
		if (TragicConfig.allowIre) registerEvent(new ServerTickEvents());

		if (TragicConfig.allowChallengeScrolls && TragicConfig.allowNonMobItems && TragicConfig.allowNonMobBlocks && TragicConfig.allowMobs && TragicConfig.allowDimension)
		{
			TragicItems.initializeChallengeItem();
			registerEvent(new ChallengeItemEvents());
		}
		else if (TragicConfig.allowChallengeScrolls)
		{
			TragicMC.logWarning("Challenge Scrolls are enabled in config but are disabled due to certain things being disabled. This is to prevent game crashes from ocurring.");
			TragicConfig.allowChallengeScrolls = false;
		}

		if (TragicConfig.allowNonMobItems && TragicConfig.allowNonMobBlocks) registerEvent(new DropEvents());

		NetworkRegistry.INSTANCE.registerGuiHandler(TragicMC.getInstance(), this);
		if (TragicConfig.allowDoomsdays) registerEvent(new DoomsdayManager());
		DoomsdayManager.clearRegistry();

		if (TragicConfig.allowVanillaChanges) registerEvent(new VanillaChangingEvents());
		if (TragicConfig.allowOverworldOreGen) GameRegistry.registerWorldGenerator(new tragicneko.tragicmc.worldgen.OverworldOreWorldGen(), 1);
		if (TragicConfig.allowNetherOreGen) GameRegistry.registerWorldGenerator(new tragicneko.tragicmc.worldgen.NetherOreWorldGen(), 2);
		if (TragicConfig.allowFlowerGen) GameRegistry.registerWorldGenerator(new FlowerWorldGen(), 3);

		if (TragicConfig.allowDimension && TragicConfig.allowFlowerGen)
		{
			FlowerWorldGen.allowedBiomes.add(TragicBiome.PaintedClearing);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.PaintedForest);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.PaintedHills);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.PaintedPlains);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.AshenBadlands);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.AshenHills);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.AshenMountains);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.DarkForest);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.DarkForestHills);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.DarkMarsh);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.HallowedHills);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.HallowedForest);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.HallowedCliffs);
			FlowerWorldGen.allowedBiomes.add(TragicBiome.HallowedPrarie);
		}

		if (TragicConfig.allowStructureGen) GameRegistry.registerWorldGenerator(new tragicneko.tragicmc.worldgen.StructureWorldGen(), 10);

		if (TragicConfig.allowNetwork)
		{
			net = new SimpleNetworkWrapper(TragicMC.MODID);
			net.registerMessage(MessageHandlerDoom.class, MessageDoom.class, 0, Side.CLIENT);
			net.registerMessage(MessageHandlerAmulet.class, MessageAmulet.class, 1, Side.CLIENT);
			net.registerMessage(MessageHandlerGui.class, MessageGui.class, 2, Side.SERVER);
			net.registerMessage(MessageHandlerUseDoomsday.class, MessageUseDoomsday.class, 3, Side.SERVER);
			net.registerMessage(MessageHandlerFlight.class, MessageFlight.class, 4, Side.CLIENT);
			net.registerMessage(MessageHandlerAttack.class, MessageAttack.class, 5, Side.SERVER);
			net.registerMessage(MessageHandlerSpawnParticle.class, MessageParticle.class, 6, Side.CLIENT);
			net.registerMessage(MessageHandlerFrozenInput.class, MessageFrozenInput.class, 8, Side.SERVER);
		}

		if (TragicConfig.allowAchievements && TragicConfig.allowNonMobItems && TragicConfig.allowNonMobBlocks && TragicConfig.allowChallengeScrolls && TragicConfig.allowAmulets && TragicConfig.allowDoom && TragicConfig.allowDoomsdays && TragicConfig.allowDimension) //register achievements after everything else is processed
		{
			TragicAchievements.load(); 
			registerEvent(new AchievementEvents());
		}
		else if (TragicConfig.allowAchievements)
		{
			TragicMC.logWarning("Achievements are enabled in config but are disabled due to certain blocks and items being disabled. This is to prevent game crashes from ocurring.");
			TragicConfig.allowAchievements = false;
		}
	}

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiId == AMULET_GUI_ID && PropertyAmulets.get(player) != null)
		{
			return new ContainerAmulet(player, player.inventory, PropertyAmulets.get(player).inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		if (guiId == AMULET_GUI_ID && PropertyAmulets.get(player) != null)
		{
			return new GuiAmuletInventory(player, player.inventory, PropertyAmulets.get(player).inventory);
		}
		return null;
	}

	public EntityPlayer getPlayerFromMessageCtx(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}

	public static void registerEvent(Object o)
	{
		MinecraftForge.EVENT_BUS.register(o);
	}

	public Logger getLogger() {
		return logger;
	}
}
