package com.macj7.loanapp.service;

import com.macj7.loanapp.model.Loan;
import com.macj7.loanapp.model.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author macj7
 */

public interface LoanManager extends Serializable {
    
    /**
     *
     * @param name
     * @param permission
     * @param pcode
     * @return returns true if registration successful
     */
    public boolean registerUser(String name, String pcode, int permission);
    
    
    
    public boolean passCodeInUse(String pcode);
    public User loginUser(String pcode);
    public User getUser(int id);
    public List<User> getCustomers();
    public Loan getLoan(int id);
    public List<Loan> getActiveLoans();
    public List<Loan> getActiveLoans(int id);
    public boolean createLoan(int loan_status_id, User officer, User cust, BigDecimal loan_amt);
    public boolean createRepayment(Loan loan, BigDecimal amt_repaid);
    public boolean updateRepayment(Loan loan, BigDecimal new_payment);
    public boolean deleteRepayment(Loan loan);
    
}
