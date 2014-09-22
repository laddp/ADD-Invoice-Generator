/*
 * Created on Aug 30, 2007 by pladd
 *
 */
package com.bottinifuel.contract_file;

import java.io.PrintStream;


/**
 * @author pladd
 *
 */
public class ContractTaxLine extends Record
{
    public final int TotalCents;
    
    public ContractTaxLine(int totalCents)
    {
        super(RecType.ContractTaxLine);
        
        TotalCents = totalCents;
    }

    public void Write(PrintStream out)
    {
        super.Write(out);
        
        out.printf("%09d%09d\n",
                   0,
                   TotalCents);
    }
}