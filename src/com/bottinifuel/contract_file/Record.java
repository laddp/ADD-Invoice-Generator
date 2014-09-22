/*
 * Created on Aug 28, 2007 by pladd
 *
 */
package com.bottinifuel.contract_file;

import java.io.PrintStream;

/**
 * @author pladd
 *
 */
public class Record
{
    public enum RecType
    {
        InfoHeader      ( 1),
        DocHeader       ( 2),
        Division        ( 3),
        PostCode        ( 4),
        PromptPayMsg    ( 5),
        ContractInfo    ( 5),
        Statement       (11),
        LineItem        (12),
        CreditInfo      (13),
        DiscntInfo      (14),
        FinChgInfo      (15),
        ContractFlags   (16),
        MeterInfo       (17),
        ContractLine    (18),
        ContractTaxLine (19),
        LineItemAddr    (26),
        StatementAddr   (28),
        Trailer         (99);
        
        public final int Code;
        RecType(int code) { Code = code; }
    }
    
    public final RecType Type;
    public Record(RecType t)
    {
        Type = t;
    }
    
    public void Write(PrintStream out)
    {
        out.printf("%02d", Type.Code);
    }
}
