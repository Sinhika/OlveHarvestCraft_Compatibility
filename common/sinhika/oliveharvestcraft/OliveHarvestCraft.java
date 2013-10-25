package sinhika.oliveharvestcraft;


import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * module OliveHarvestCraft_Compatibility's main class.
 * @author cyhiggin
 *
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod( clientSideRequired = true,	serverSideRequired = false)
public class OliveHarvestCraft 
{
    /** The instance of your mod that Forge uses. */
    @Instance(ModInfo.ID)
    public static OliveHarvestCraft INSTANCE;
    
    /** important IDs gleaned from OliveCraft & HarvestCraft */
    public Item oc_olive;
    public Item oc_oliveOil;
    public Item hc_olive;
    public Item hc_cookingOil;
    
    final public static int OIL_CONVERSION_RATE = 3;
    
    private Class<?> oc_class;
    private Class<?> hc_class;
    
    /** Says where the client and server 'proxy' code is loaded. */
    @SidedProxy(clientSide = "sinhika.oliveharvestcraft.ClientProxy", 
    			serverSide = "sinhika.oliveharvestcraft.CommonProxy")
    public static CommonProxy proxy;

    /**
     * preInit phase actions go here, such as reading config files and setting
     * up logger.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    } // end preInit()

	/**
     * Load phase actions go here, such as creating items & blocks, adding
     * recipes, etc.
     */
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
    	getModItems();
    	addRecipes();
    } // end load()

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    } // end postInit()
    
    public static void addRecipes()
    {
    	// add OliveCraft olives to the ore dictionary.
    	ItemStack oliveCraft_olive = new ItemStack(INSTANCE.oc_olive);
    	OreDictionary.registerOre("cropOlive", oliveCraft_olive);
    	
    	ItemStack harvestCraft_olive = new ItemStack(INSTANCE.hc_olive);
    	
    	// convert between OliveCraft olives and HarvestCraft olives & vice versa.
    	GameRegistry.addShapelessRecipe(oliveCraft_olive, 
    									new Object [] {harvestCraft_olive});
    	GameRegistry.addShapelessRecipe(harvestCraft_olive, 
										new Object [] {oliveCraft_olive});
    	
    	// convert OliveCraft oil to several HarvestCraft cooking oil, due to higher
    	// cost of making olive oil.
    	ItemStack harvestCraft_oil = 
    			new ItemStack(INSTANCE.hc_cookingOil, OIL_CONVERSION_RATE);
    	ItemStack oliveCraft_oil =
    			new ItemStack(INSTANCE.oc_oliveOil);
    	GameRegistry.addShapelessRecipe(harvestCraft_oil, 
    									new Object [] {oliveCraft_oil});
    	// TODO - should this be configurable? Probably	
    } // end addRecipes
    
    /** 
     * get various items, ids and other handles from the OliveCraft and
     * HarvestCraft mods. Use reflection.
     */
    private void getModItems() 
    {
    	// get the key classes from OliveCraft and HarvestCraft that we need.
    	try {
    		oc_class = Class.forName("joserichi.olivecraft.common.OliveCraft");
    		hc_class = Class.forName("assets.pamharvestcraft.PamHarvestCraft");
    	} catch (ClassNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	// get relevant item fields.
    	try {
			Field itemField = oc_class.getField("olive");
			oc_olive = (Item) itemField.get(null);
			itemField = oc_class.getField("oil");
			oc_oliveOil = (Item) itemField.get(null);
			
			itemField = hc_class.getField("oliveItem");
			hc_olive = (Item) itemField.get(null);
			itemField = hc_class.getField("oliveoilItem");
			hc_cookingOil = (Item) itemField.get(null);
		} 
    	catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
    	catch (SecurityException e)	{
			e.printStackTrace();
		}
    	catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
    	catch ( IllegalAccessException e) {
			e.printStackTrace();
		}
    } // end getModItems()


} /* end class OliveHarvestCraft */
