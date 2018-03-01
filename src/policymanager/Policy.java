package policymanager;

class Policy
{

    private String date;
    private String uppercaseRef;
    private int noOfGadgets;
    private int gadgetValue;
    private double excessValidation;
    private double premium;
    private boolean annualCoverOption;
    private String clientName;

    
     public Policy()
    {
        this.date = "";
        this.uppercaseRef = "";
        this.noOfGadgets = 0;
        this.gadgetValue = 0;
        this.excessValidation = 0;
        this.premium = 0;
        this.annualCoverOption = false;
        this.clientName = "";
    }
    
    public Policy(String clientName, String date, String uppercaseRef, int noOfGadgets, int gadgetValue,
            double excessValidation, double premium, boolean annualCoverOption)
    {
        this.date = date;
        this.uppercaseRef = uppercaseRef;
        this.noOfGadgets = noOfGadgets;
        this.gadgetValue = gadgetValue;
        this.excessValidation = excessValidation;
        this.premium = premium;
        this.annualCoverOption = annualCoverOption;
        this.clientName = clientName;
    }

   

    public String getClientName()
    {
        return this.clientName;
    }
    
    public String setClientName(String clientName)
    {
       String n = clientName;
       
       return n;
    }

    public String getDate()
    {
        return this.date;
    }

    public String setDate(String date)
    {
        String d = date;

        return d;
    }
    public int getNoOfGadgets()
    {
        return this.noOfGadgets;
    }

    public int setNoOfGadgets(int noOfGadgets)
    {
        int gadg = noOfGadgets;

        return gadg;
    }
    public double getExcessValidation()
    {
        return this.excessValidation;
    }

    public double setExcessValidation(double excessValidation)
    {
        double excess = excessValidation;

        return excess;
    }
    public double getPremium()
    {
        return this.premium;
    }
    
    

    public double setPremium(double premium)
    {
        double premiumB = premium;

        return premiumB;
    }

}
