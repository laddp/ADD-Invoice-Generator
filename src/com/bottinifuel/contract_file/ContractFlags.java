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
public class ContractFlags extends Record
{
    public final boolean Budget;
    public ContractFlags(boolean isBudget)
    {
        super(RecType.ContractFlags);
        
        Budget = isBudget;
    }

    public void Write(PrintStream out)
    {
        super.Write(out);
        
        out.printf("%1d%1d\n",
                   Budget?1:3,
                   Budget?1:0);
    }
}
