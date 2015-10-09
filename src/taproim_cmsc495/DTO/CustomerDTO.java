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
public class CustomerDTO {
    private String custID;
    private String custName;
    private String custAddress;
    private String custEmail;
    
    /**
     * Constructor ensures that all fields have been initialized
     */
    public CustomerDTO() {
        custID = custName = custAddress = custEmail = "";
    }
    
    // Table Field SETTER Methods @param String value to be set
    public void setCustID(String custID)            { this.custID        = cleanInput(custID);       }
    public void setCustName(String custName)        { this.custName      = cleanInput(custName);     }
    public void setCustAddress(String custAddress)  { this.custAddress   = cleanInput(custAddress);  }
    public void setCustEmail(String custEmail)      { this.custEmail     = cleanInput(custEmail);    }
    
    // Table field GETTER methods @return requested field
    public String getCustID()                       { return this.custID;       }
    public String getCustName()                     { return this.custName;     }
    public String getCustAddress()                  { return this.custAddress;  }
    public String getCustEmail()                    { return this.custEmail;    }
    
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
