/*
 * Created on Aug 28, 2007 by pladd
 *
 */
package com.bottinifuel.contract_file;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pladd
 *
 */
public class ContractStatementRec extends Record
{
    public final int     AcctNum;
    public final int     Division;
    public final int     Type;
    
    public final String  Name;
    public final String  Addr1;
    public final String  Addr2;
    public final String  Town;
    public final String  State;
    public final String  Zip;
        
    public final boolean SpecialHandling;

    private int TotalDueCents;
    
    private List<Record> Records = new ArrayList<Record>(3);
    
    public ContractStatementRec(int acctNum, 
                                String name, String addr1, String addr2, 
                                String town, String state, String zip,
                                int division, int type, boolean special)
    throws Exception
    {
        super(RecType.Statement);
        AcctNum = acctNum;
        
        Name  = name;
        Addr1 = addr1;
        Addr2 = addr2;
        Town  = town;
        State = state;
        Zip   = zip;
        
        Division = division;
        Type     = type;
        SpecialHandling = special;
    }
    
    public void AddRecord(Record r)
    {
        Records.add(r);
    }
    
    public void Write(PrintStream out)
    {
        super.Write(out);
        
        out.printf("%-6d%02d%1d" +
                   "%-30.30s%-30.30s%-30.30s%-20.20s%-10.10s%9.9s" + // name/addr/zip
                   "%09d%09d%22s%09d%18s" + // balances
                   "%47s%2s%1s%13s%02d%12s%1s%4s%1s%-7d\n",
                   AcctNum,
                   Division,
                   Type,
                   
                   Name,
                   Addr1,
                   Addr2,
                   Town,
                   State,
                   Zip,

                   0,      // PrevBal
                   TotalDueCents,      // CurrBal
                   "",     // --filler--
                   TotalDueCents,
                   "",     // --filler--

                   "",     // OCR
                   "",     // --filler--
                   " ",    // Open item flag
                   "",     // --filler--
                   1,      // Company code on forms
                   "",     // --filler--
                   SpecialHandling?"Y":"N", // Special handling flag
                   "",     // --filler--
                   " ",    // Debit/credit card indicator
                   AcctNum // Expanded acct #
                  );
        
        for (Record r : Records)
        {
            r.Write(out);
        }
    }

    public int getTotalDueCents()
    {
        return TotalDueCents;
    }

    public void setTotalDueCents(int totalDueCents)
    {
        TotalDueCents = totalDueCents;
    }

    public String getName()
    {
        return Name;
    }

    public String getAddr1()
    {
        return Addr1;
    }

    public String getAddr2()
    {
        return Addr2;
    }

    public String getTown()
    {
        return Town;
    }

    public String getState()
    {
        return State;
    }

    public String getZip()
    {
        return Zip;
    }

}
