package com.macj7.loanapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.macj7.loanapp.model.Loan;
import com.macj7.loanapp.model.User;
import com.macj7.loanapp.service.PassCodeGenerator;
import com.macj7.loanapp.service.SimpleLoanManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author macj7
 */
@Controller
public class LoanAppController {
    
    protected final Log logger = LogFactory.getLog(getClass());
    private final SimpleLoanManager manager = new SimpleLoanManager();
    private final int pcode_length = 8;
    
    
    
    @RequestMapping(value="/",method=RequestMethod.GET)
    public ModelAndView root(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ////////////////////////////////////////////////
        
        /**
         * In the code section below if section to see if a user is signed in
         * by checking for a session attribute "user" which should exist after
         * a logging. If not, then the index view is rendered. If it does, then
         * the user is redirect to the customer view or officer view depending
         * on which of these two the user is.
         */
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            if (user.getPermission()==2) {
                return new ModelAndView("redirect:/customer");
            }
            else {
                return new ModelAndView("redirect:/officer");
            }
        }
        //////////////////////////////////////////////////////////
        return new ModelAndView("redirect:/index");
    }
    
    

    /***
     * 
     * This controller method is mapped to the url /index
     * 
     * @param request
     * @return ModelAndView Object.
     */
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ////////////////////////////////////////////////
        
        /**
         * In the code section below if section to see if a user is signed in
         * by checking for a session attribute "user" which should exist after
         * a logging. If not, then the index view is rendered. If it does, then
         * the user is redirect to the customer view or officer view depending
         * on which of these two the user is.
         */
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            if (user.getPermission()==2) {
                return new ModelAndView("redirect:/customer");
            }
            else {
                return new ModelAndView("redirect:/officer");
            }
        }
        //////////////////////////////////////////////////////////
        return new ModelAndView("index");
    }
    
    /***
     * The following controller method is mapped to the url /signUp
     * and serves up the view for this url.
     * 
     * @param request
     * @return ModelAndView Object
     */
    @RequestMapping(value="/signUp",method=RequestMethod.GET)
    public ModelAndView signUp(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ////////////////////////////////////////////////
        
        /**
         * In the code section below if section to see if a user is signed in
         * by checking for a session attribute "user" which should exist after
         * a logging. If not, then the index view is rendered. If it does, then
         * the user is redirect to the customer view or officer view depending
         * on which of these two the user is.
         */
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            if (user.getPermission()==2) {
                return new ModelAndView("redirect:/customer");
            }
            else {
                return new ModelAndView("redirect:/officer");
            }
        }
        //////////////////////////////////////////////////////////
        return new ModelAndView("signUp");
    }
    
    /***
     * This method controller is mapped to the url /register with method post.
     * This controller method is responsible for creating a new
     * customer and adding them to the database.
     * 
     * @param request
     * @return ModelAndView Object
     */
    @RequestMapping(value="/register",method=RequestMethod.POST)
    public ModelAndView register(HttpServletRequest request) {
        
        //checks the http request for user's first name.
        String fname = request.getParameter("fname");
        if ((fname == null) || (fname.equals(""))) {
            return new ModelAndView("redirect:/failed"); //fails if there is no first name or is empty string.
        }
        
        //checks the http request for user's last name.
        String lname = request.getParameter("lname");
        if (lname == null) {
            return new ModelAndView("redirect:/index"); // fails if there is no last name or empty string.
        }
        
        String name = fname+" "+lname; // concatenate them into just a name.
        
        String pcode = "";
        PassCodeGenerator gen = (new PassCodeGenerator()); // creates a PassCodeGenerator object.
        
        // generate pass codes until a unique one that is not in the database is generated.
        do {
            pcode = gen.getPassCode(pcode_length);
        }while (manager.passCodeInUse(pcode));
        
        // displays a view with the pass code of the new user if registration is successful
        if (manager.registerUser(name, pcode, 2)) {
            return new ModelAndView("register","pcode",pcode);
        }
        return new ModelAndView("redirect:/failed"); // displays a page indicating that registration has failed.
    }
    


    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request) {
        String pcode = request.getParameter("pcode");
        if (pcode == null) {
            return new ModelAndView("redirect:/index");
        }
        HttpSession session = request.getSession();
        
        if (pcode.length() != pcode_length) {
            return new ModelAndView("redirect:/index");
        }
        
        if (manager.passCodeInUse(pcode)) {
            System.out.println("In login-pcode:"+pcode);
            session.setAttribute("pcode", pcode);
            
            User user = manager.loginUser(pcode);
            session.setAttribute("user", user);
            System.out.println("In login-permission:"+user.getPermission());
            if (user.getPermission()==2) {
                return new ModelAndView("redirect:/customer");
            }
            else {
                return new ModelAndView("redirect:/officer");
            }
        }
        else {
            return new ModelAndView("redirect:/index");
        }
        
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        
        return new ModelAndView("redirect:/index");
    }
    

    @RequestMapping(value="/customer",method=RequestMethod.GET)
    public ModelAndView customer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ////////////////////////////////////////////////
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            if (user.getPermission()==2) {
                return new ModelAndView("customer");
            }
            else {
                return new ModelAndView("redirect:/401"); //redirect 401 error page.
            }
        }
        //////////////////////////////////////////////////////////
        
        return new ModelAndView("redirect:/401"); //redirect 401 error page.
    }
    
    @RequestMapping(value="/officer",method=RequestMethod.GET)
    public ModelAndView officer(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ////////////////////////////////////////////////
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            if (user.getPermission()==2) {
                return new ModelAndView("redirect:/401"); //redirect 401 error page.
            }
            else {
                return new ModelAndView("officer");
            }
        }
        //////////////////////////////////////////////////////////
        return new ModelAndView("redirect:/401"); //redirect 401 error page.
    }
    

    @RequestMapping(value="/getActiveLoans", headers = "Accept=application/json", method=RequestMethod.GET)
    public @ResponseBody List<Map<String,String>> getActiveLoans(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user==null) {

            return null;
        }

        List<Loan> loans = manager.getActiveLoans(user.getUsrId());

        List<Map<String,String>> json = new ArrayList();
        Map<String,String> map;

        
        for (Loan loan : loans) {
            map = new HashMap<>();
            String tmp=null;
            
            map.put("loan_no", loan.getLoanNo().toString());
            
            if (loan.getLoanStatusId()==1){
                tmp = "Active";
            }
            else {
                tmp = "Closed";
            }
            
            map.put("loan_status", tmp);
            map.put("officer",  loan.getUserByOfficerIdNo().getLoanOfficer().getOfficerName() );
            map.put("customer",  loan.getUserByCustIdNo().getCustomer().getCustName() );
            map.put("loan_amt", loan.getLoanAmt().toPlainString());
            map.put("amt_repaid", "0");
            
            if (loan.getRepayment()==null) {
                map.put("flag1", "false");
                map.put("flag2", "true");
            }
            else {
                map.put("flag1", "true");
                map.put("flag2", "false");
            }
            

            json.add(map);
        }
        
        
        return json;
    }
    
    
    
    @RequestMapping(value="/getCustomers", headers = "Accept=application/json", method=RequestMethod.GET)
    public @ResponseBody List<Map<String,String>> getCustomers(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user==null) {

            return null;
        }

        List<User> users = manager.getCustomers();
        
        List<Map<String,String>> json = new ArrayList();

        
        for (User usr : users) {
            Map<String,String> map = new HashMap<>();
            String tmp=null;
            
            map.put("id",usr.getUsrId().toString());
            map.put("name", usr.getCustomer().getCustName());
            map.put("amt_owed", "0");

            json.add(map);
        }
        
        
        return json;
    }
    
    
    
    @RequestMapping(value="/createRepayment", method=RequestMethod.POST)
    public @ResponseBody void createRepayment(HttpServletRequest request) {
        BigDecimal amt = new BigDecimal(request.getParameter("amt"));

        int id = new Integer(request.getParameter("id"));

        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {

            Loan loan = manager.getLoan(id);
        
            // creating repayment.
            manager.createRepayment(loan, amt);
        }
    }
    
    
    
    @RequestMapping(value="/updateRepayment", method=RequestMethod.POST)
    public @ResponseBody void updateRepayment(HttpServletRequest request) {
        BigDecimal amt = new BigDecimal(request.getParameter("amt"));

        int id = new Integer(request.getParameter("id"));

        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {

            Loan loan = manager.getLoan(id);

        
            // updating repayment.
            manager.updateRepayment(loan, amt);
        }
    }
    
    
    @RequestMapping(value="/deleteRepayment", method=RequestMethod.POST)
    public @ResponseBody void deleteRepayment(HttpServletRequest request) {
        int id = new Integer(request.getParameter("id"));
        
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {

            Loan loan = manager.getLoan(id);
        
            // deleting repayment.

            manager.deleteRepayment(loan);
        }
    }
    
    
    
    @RequestMapping(value="/createLoan", method=RequestMethod.POST)
    public @ResponseBody void createLoan(HttpServletRequest request) {
        
        BigDecimal amt = new BigDecimal(request.getParameter("amt"));
        int cust_id = new Integer(request.getParameter("cust_id_no"));
        
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            User customer = manager.getUser(cust_id);
        
            // deleting repayment.

            manager.createLoan(1, user, customer, amt);
        }
    }
    
    
    
    @RequestMapping(value="/getCurrentUser", headers = "Accept=application/json", method=RequestMethod.GET)
    public @ResponseBody Map<String,String> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        if (user==null) {

            return null;
        }


        Map<String ,String> json = new HashMap<>();
        json.put("id", user.getUsrId().toString());
        if (user.getPermission()==2) {
            json.put("name", user.getCustomer().getCustName());
        }
        else {
            json.put("name", user.getLoanOfficer().getOfficerName());
        }
        
        
        return json;
    }
    
    
    
    @RequestMapping(value="/401")
    public ModelAndView error401() {
        return new ModelAndView("401");
    }
    
}
