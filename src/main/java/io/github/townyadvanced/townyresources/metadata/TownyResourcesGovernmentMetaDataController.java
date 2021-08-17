package io.github.townyadvanced.townyresources.metadata;

import com.gmail.goosius.siegewar.SiegeWar;
import com.palmergames.bukkit.towny.object.Government;
import io.github.townyadvanced.townyresources.objects.ResourceQuantity;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Goosius
 *
 */
public class TownyResourcesGovernmentMetaDataController {

	@SuppressWarnings("unused")
	private SiegeWar plugin;

	public TownyResourcesGovernmentMetaDataController(SiegeWar plugin) {
		this.plugin = plugin;
	}

    private static String
        discoveredMetadataKey = "townyresources_discovered",  //e.g.   OAK_LOG, SUGAR
        dailyProductionMetadataKey = "townyresources_dailyproduction",  //e.g.   32-OAK_LOG, 32-SUGAR
        availableForCollectionMetadataKey = "townyresources_availableforcollection";  //e.g.  64-OAK_LOG, 64-SUGAR

    public static String getDiscovered(Government government) {
        return MetaDataUtil.getSdf(government, discoveredMetadataKey).replaceAll(" ","");
    }
    
    public static void setDiscovered(Government government, List<String> discoveredResources) {
        //Convert materials list to single string
        StringBuilder metadataStringBuilder = new StringBuilder();
        for(int i= 0; i < discoveredResources.size();i++) {
            if(i !=0)
                metadataStringBuilder.append(", "); 
            metadataStringBuilder.append(discoveredResources.get(i));
        }
        setDiscovered(government, metadataStringBuilder.toString());
    }

    public static void setDiscovered(Government government, String discovered) {
        MetaDataUtil.setSdf(government, discoveredMetadataKey, discovered);
    }
    
    public static String getDailyProduction(Government government) {
        return MetaDataUtil.getSdf(government, dailyProductionMetadataKey).replaceAll(" ","");
    }

    public static void setDailyProduction(Government government, List<String> dailyProduction) {
        StringBuilder dailyProductionStringBuilder = new StringBuilder();
        for(int i = 0; i < dailyProduction.size(); i++) {
            //Add comma before all entries except 1st
            if(i != 0) 
                dailyProductionStringBuilder.append(", ");
            dailyProductionStringBuilder.append(dailyProduction.get(i));
        }
        MetaDataUtil.setSdf(government, dailyProductionMetadataKey, dailyProductionStringBuilder.toString());
    }

    public static void setDailyProduction(Government government, String dailyProduction) {
        MetaDataUtil.setSdf(government, dailyProductionMetadataKey, dailyProduction);
    }

    public static String getAvailableForCollection(Government government) {
        return MetaDataUtil.getSdf(government, availableForCollectionMetadataKey).replaceAll(" ","");
    }

    public static void setCollectedResources(Government government, String availableForCollection) {
        MetaDataUtil.setSdf(government, availableForCollectionMetadataKey, availableForCollection);
    }

    public static List<ResourceQuantity> getAvailableForCollectionAsList(Government town) {
        List<ResourceQuantity> result = new ArrayList<>();
        String collectedResourcesAsString = getAvailableForCollection(town);
        if(!collectedResourcesAsString.isEmpty()) {
            String[] collectedResourcesAsArray = collectedResourcesAsString.toUpperCase().split(",");
            String[] collectedResourceMaterialAmount;
            Material material;
            int amount;            
            ResourceQuantity resourceQuantity;
            for(String collectedResource: collectedResourcesAsArray) {
                collectedResourceMaterialAmount = collectedResource.split("-");
                material = Material.getMaterial(collectedResourceMaterialAmount[0]);
                amount = Integer.parseInt(collectedResourceMaterialAmount[1]);
                resourceQuantity = new ResourceQuantity(material, amount);
                result.add(resourceQuantity);                
            }
        }
        return result;
    }
}