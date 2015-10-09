/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taproim_cmsc495.DTO;

/**
 *
 * @author kakenyon32
 */
public class InventoryDTO {
    //private variable associated with inventory objects
    private String itemID, description, stockLevel, weight;
    
    /**
     * Constructor ensures that all variables have been initialized
     */
    public InventoryDTO() {
        itemID = description = stockLevel = weight = "";
    }
    
    // Table Field SETTER Methods @param String value to be set
    public void setItemID(String itemID)            { this.itemID        = cleanInput(itemID);      }
    public void setDescription(String description)  { this.description   = cleanInput(description); }
    public void setStockLevel(String stockLevel)    { this.stockLevel    = cleanInput(stockLevel);  }
    public void setWeight(String weight)            { this.weight        = cleanInput(weight);      }
    
    // Table field GETTER methods @return requested field
    public String getItemID()                       { return this.itemID;       }
    public String getDescription()                  { return this.description;  }
    public String getStockLevel()                   { return this.stockLevel;   }
    public String getWeight()                       { return this.weight;       }
    
      /**
     * @param text - the text to be "cleaned"
     * @return the "cleaned" text
     */
    private String cleanInput(String input) {
        String output = "";
        char[] characters = {
            ':', ';', '/', '\\', '\'', '?', '!', 
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
