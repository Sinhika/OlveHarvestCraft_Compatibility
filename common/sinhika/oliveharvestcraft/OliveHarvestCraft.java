package sinhika.oliveharvestcraft;


import joserichi.olivecraft.common.OliveCraft;
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

/**
 * module OliveHarvestCraft_Compatibility's main class.
 * @author cyhiggin
 *
 */
@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL }, clientSideRequired = true, 
			serverSideRequired = false)
public class OliveHarvestCraft 
{
    /** The instance of your mod that Forge uses. */
    @Instance(ModInfo.ID)
    public static OliveHarvestCraft instance;
    
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
    	addRecipes();
    } // end load()

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    } // end postInit()
    
    public static void addRecipes()
    {
    	// add OliveCraft olives to the ore dictionary.
    	ItemStack oliveCraft_olive = new ItemStack(OliveCraft.olive);
    	OreDictionary.registerOre("cropOlive", oliveCraft_olive);
    	
    	// convert between OliveCraft olives and HarvestCraft olives & vice versa.
    	// TODO may need to use reflection here.
    	
    	// convert OliveCraft oil to several HarvestCraft cooking oil, due to higher
    	// cost of making olive oil.
    	// TODO may need to use reflection here.
    	// TODO - should this be configurable?   	
    } // end addRecipes
} /* end class OliveHarvestCraft */
