/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taproim_cmsc495;

/**
 * This class provides the data fields for Shipments
 * @author Dent, Gorder, Kenyon, Montoya, Ward
 */
public class ShipmentDTO {
    private String shipID, itemID, custID, destination, location, 
            weight, numItems, trackingNum, carrier, signer;
    
    // Table Field SETTER Methods @param String value to be set
    public void setShipID(String shipID)            { this.shipID = cleanInput(shipID);           }
    public void setItemID(String itemID)            { this.itemID = cleanInput(itemID);           }
    public void setCustID(String custID)            { this.custID = cleanInput(custID);           }
    public void setDestination(String destination)  { this.destination = cleanInput(destination); }
    public void setLocation(String location)        { this.location = cleanInput(location);       }
    public void setWeight(String weight)            { this.weight = cleanInput(weight);           }
    public void setNumItems(String numItems)        { this.numItems = cleanInput(numItems);       }
    public void setTrackingNum(String trackingNum)  { this.trackingNum = cleanInput(trackingNum); }
    public void setCarrier(String carrier)          { this.carrier = cleanInput(carrier);         }
    public void setSigner(String signer)            { this.signer = cleanInput(signer);           }
    
    // Table field GETTER methods @return requested field
    public String getShipID()                       { return shipID;        }
    public String getItemID()                       { return itemID;        }
    public String getCustID()                       { return custID;        }
    public String getDestination()                  { return destination;   }
    public String getLocation()                     { return location;      }
    public String getWeight()                       { return weight;        }
    public String getNumItems()                     { return numItems;      }
    public String getTrackingNum()                  { return trackingNum;   }
    public String getCarrier()                      { return carrier;       }
    public String getSigner()                       { return signer;        }
    
    /**
     * @param text - the text to be "cleaned"
     * @return the "cleaned" text
     */
    private String cleanInput(String input) {
        String output = "";
        char[] characters = {
            '@', ':', ';', '/', '\\', '\'', '?', '!', 
            '#', '$', '%', '^', '&',  '*',  '(', ')'
        };
        char[] cleaning = input.toCharArray();
        
        for (int i = 0; i < cleaning.length; i++) 
            for (char sc : characters) 
                if (cleaning[i] == sc) cleaning[i] = '~';
        for (int i = 0; i < cleaning.length; i ++) 
            if (cleaning[i] != '~') output += cleaning[i];
        
        return output;
    }
}
