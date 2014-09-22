/*
 * Created on Aug 28, 2007 by pladd
 *
 */
package com.bottinifuel.contract_file;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * @author pladd
 *
 */
public class ContractLine extends Record
{
    public final Calendar TransDate;
    public final int      PostCode;
    public final char     Contract;
    public final int      BPC = 0;
    public final int      BSL = 0;
    public final int      PriceCents;
    public final int      DiscountCents = 0;
    public final int      RefNum;
    public final String   SvcAddr;
    public final String   CoveragePeriod = "";
    public final char     SvcRenewalPeriod = 'A'; // A or M
    public final int      ConsecBillingMonthsLeft = 0;
    public final int      ConsecBillingMonths = 0;
    
    public final int      ContractDevCents = 0;
    public final int      SvcLoc;
    
    private static final DateFormat df = new SimpleDateFormat("MMddyy");
    
    
    public ContractLine(Calendar date, int postCode, char contr, int price,
                        int refNum, String addr, int loc)
    {
        super(RecType.ContractLine);
        
        TransDate  = date;
        PostCode   = postCode;
        Contract   = contr;
        PriceCents = price;
        RefNum     = refNum;
        SvcAddr    = addr;
        SvcLoc     = loc;
    }
    
    
    public void Write(PrintStream out)
    {
        super.Write(out);
        
        out.printf("%6s%03d%c%02d%02d" +
                   "%09d%09d%09d%-60.60s%-25s%c" +
                   "%02d%02d%09d%03d\n",
                   df.format(TransDate.getTime()),
                   PostCode,
                   Contract,
                   BPC,
                   BSL,
                   
                   PriceCents,
                   DiscountCents,
                   RefNum,
                   SvcAddr,
                   CoveragePeriod,
                   SvcRenewalPeriod,
                   
                   ConsecBillingMonthsLeft,
                   ConsecBillingMonths,
                   ContractDevCents,
                   SvcLoc
                   );
    }
}
