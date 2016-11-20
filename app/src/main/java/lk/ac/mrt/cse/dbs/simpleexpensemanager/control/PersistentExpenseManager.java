package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by ThirasaraDevanmini on 11/20/2016.
 */

public class PersistentExpenseManager extends ExpenseManager {

    private Context ctx;

    //constructor
    public PersistentExpenseManager(Context ctx) throws ExpenseManagerException {
        this.ctx = ctx;
        setup();
    }



    @Override
    public void setup() throws ExpenseManagerException {
        //create the database
        SQLiteDatabase mydatabase = ctx.openOrCreateDatabase("140042R", ctx.MODE_PRIVATE, null);

        //If it's the first time, we have to create the databases.
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Account(" +
                "Account_no VARCHAR PRIMARY KEY," +
                "Bank VARCHAR," +
                "Holder VARCHAR," +
                "Initial_amt REAL" +
                " );");

        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TransactionLog(" +
                "Transaction_id INTEGER PRIMARY KEY," +
                "Account_no VARCHAR," +
                "Type INT," +
                "Amt REAL," +
                "Log_date DATE," +
                "FOREIGN KEY (Account_no) REFERENCES Account(Account_no)" +
                ");");



        //These two functions will hold our DAO instances in memory till the program exists
        PersistentAccountDAO accountDAO = new PersistentAccountDAO(mydatabase);
        //accountDAO.addAccount(new Account("Account12","Sampath bank","Manujith",500));

        setAccountsDAO(accountDAO);

        setTransactionsDAO(new PersistentTransactionDAO(mydatabase));

    }
}
