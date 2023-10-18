package ca.mcgill.ecse.assetplus.controller;

import java.sql.Date;

public class AssetPlusFeatureSet3Controller {

  public static String addSpecificAsset(int assetNumber, int floorNumber, int roomNumber,
      Date purchaseDate, String assetTypeName) {
        AssetType assetType = AssetType.getWithName(assetTypeName); 
        if(assetType == null){
          return "Asset Type provided does not exist";
        }
        else{
          SpecificAsset newSpecificAsset = assetType.addSpecificAsset(assetNumber, floorNumber, roomNumber, purchaseDate, assetType.getAssetPlus());
          if(assetType.addSpecificAsset(newSpecificAsset)){
            return "Specific Asset added successfully!";
          }
          else{
            return "Error adding Specific Asset";
          }
        }
  }

  public static String updateSpecificAsset(int assetNumber, int newFloorNumber, int newRoomNumber,
      Date newPurchaseDate, String newAssetTypeName) {
        AssetType assetType = AssetType.getWithName(newAssetTypeName); 
        if (assetType == null){
          return "Asset Type provided does not exist";
        }
        else{
          SpecificAsset toUpdate = SpecificAsset.getWithAssetNumber(assetNumber);
          if(toUpdate == null){
            return "Specific Asset does not exist";
          }
          else{
            boolean updateFloor = toUpdate.setFloorNumber(newFloorNumber);
            boolean updateRoom = toUpdate.setRoomNumber(newRoomNumber);
            boolean updateDate = toUpdate.setPurchaseDate(newPurchaseDate);
            AssetType oldAssetType = toUpdate.getAssetType();
            
            if (!assetType.equals(oldAssetType)) {
              oldAssetType.removeSpecificAsset(toUpdate);
              assetType.addSpecificAsset(toUpdate);
            }
            if(updateFloor && updateRoom && updateDate){
              return "Specific Asset successfully updated";
            }
            else{
              return "Error updating Specific Asset";
            }
          }
        }
  }

  public static void deleteSpecificAsset(int assetNumber) {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

}
