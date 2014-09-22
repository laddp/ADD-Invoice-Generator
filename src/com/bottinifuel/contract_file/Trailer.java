/*
 * Created on Aug 30, 2007 by pladd
 *
 */
package com.bottinifuel.contract_file;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author pladd
 *
 */
public class Trailer extends Record
{
    public final Date FileDate;
    public final int  StmtCount;
    public final int  TotalCents;
    
    public Trailer(Date d, int count, int totalCents)
    {
        super(RecType.Trailer);
        
        FileDate   = d;
        StmtCount  = count;
        TotalCents = totalCents;
    }

    public void Write(PrintStream out)
    {
        super.Write(out);
        
        DateFormat df = new SimpleDateFormat("MMDDyyHH:mm aa");
        out.printf("%14.14s%07d%010d\n",
                   df.format(FileDate),
                   StmtCount,
                   TotalCents);
    }
}
