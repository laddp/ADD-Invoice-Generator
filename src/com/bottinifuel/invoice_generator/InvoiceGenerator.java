/*
 * Created on Aug 30, 2007 by pladd
 *
 */
package com.bottinifuel.invoice_generator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

import com.bottinifuel.Energy.Info.CustInfo;
import com.bottinifuel.Energy.Info.InfoFactory;
import com.bottinifuel.contract_file.ContractFlags;
import com.bottinifuel.contract_file.ContractLine;
import com.bottinifuel.contract_file.ContractStatementRec;
import com.bottinifuel.contract_file.ContractTaxLine;
import com.bottinifuel.contract_file.LineItemAddr;
import com.bottinifuel.contract_file.Trailer;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author pladd
 *
 */
public class InvoiceGenerator
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("Error missing args");
            System.exit(1);
        }
        
        try {
            InfoFactory info;
            try {
                info = new InfoFactory();
            }
            catch (Exception e)
            {
                throw new Exception("Error opening ADD Energy connection", e);
            }

            CSVReader reader;
            try { reader = new CSVReader(new FileReader(args[0])); }
            catch (FileNotFoundException e) { throw new Exception("Input file not found"); }

            PrintStream out = new PrintStream(args[1]);

            Calendar c = Calendar.getInstance();
            c.set(2007, 7, 15);

            int stmt_count = 0;
            int total_cents = 0;
            
            int prev_acct = 0;
            int line_num = 0;
            String [] line;
            ContractStatementRec csr = null;
            CustInfo ci = null;
            while ((line = reader.readNext()) != null) {
                if (++line_num == 1)
                    continue;
                if (line.length != 3)
                    throw new Exception("Line too short or long");

                int acct_num = Integer.valueOf(line[0]);
                if (prev_acct != acct_num)
                {
                    if (csr != null)
                    {
                        ContractTaxLine tax = new ContractTaxLine(csr.getTotalDueCents());
                        ContractFlags flags = new ContractFlags(false);

                        csr.AddRecord(tax);
                        csr.AddRecord(flags);

                        csr.Write(out);
                        stmt_count++;
                    }

                    try {
                        int short_acct = info.AccountNum(acct_num);
                        ci = new CustInfo(info, short_acct);
                    }
                    catch (Exception e)
                    {
                        throw new Exception("Error getting account info: #" + acct_num, e);
                    }

                    csr = new ContractStatementRec(acct_num, ci.Name, ci.Street1, ci.Street2,
                                                   ci.Town, ci.State, ci.Zip,
                                                   ci.Division, ci.Type, ci.InvoiceSpecialHandling);
                }
                else
                {
                    int foo = 0;
                }
                prev_acct = acct_num;

                if (ci.isBudget())
                {
                    System.err.println("Skipping budget acct #" + acct_num);
                    continue;
                }

                int price =  (int)(Double.valueOf(line[2]) * 100);
                int refNum = Integer.valueOf(line[1]);

                ContractLine cl =
                    new ContractLine(c, 115, 'Z', price, refNum, 
                                     csr.Name + "/"+ csr.Addr1 + "/"+ csr.Addr2 + "/"+
                                     csr.Town + " "+ csr.State + " "+ csr.Zip,
                                     0);
                LineItemAddr addr = new LineItemAddr(csr);
                
                total_cents += price;
                csr.setTotalDueCents(csr.getTotalDueCents() + price);
                
                csr.AddRecord(cl);
                csr.AddRecord(addr);
            }
            
            if (csr != null)
            {
                csr.Write(out);
                stmt_count++;
            }
            
            Trailer trlr = new Trailer(new Date(), stmt_count, total_cents);
            trlr.Write(out);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

}
