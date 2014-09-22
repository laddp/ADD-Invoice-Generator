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
public class LineItemAddr extends Record
{
    public final String  Name;
    public final String  Addr1;
    public final String  Addr2;
    public final String  Town;
    public final String  State;
    public final String  Zip;

    public LineItemAddr(ContractStatementRec stmt) throws Exception
    {
        super(RecType.LineItemAddr);
        
        Name  = stmt.getName();
        Addr1 = stmt.getAddr1();
        Addr2 = stmt.getAddr2();
        Town  = stmt.getTown();
        State = stmt.getState();
        Zip   = stmt.getZip();
    }

    public void Write(PrintStream out)
    {
        super.Write(out);
        
        out.printf("%-252s\n",
                   Name + "/" + Addr1 + "/" + Addr2 + "/" + Town + " " + State + " " + Zip);  
    }
}
