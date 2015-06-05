package de.budget.BudgetService;

import de.budget.BudgetService.Response.*;
import de.budget.BudgetService.Exception.*;

/**
 * Created by christopher on 01.06.15.
 */
public interface BudgetOnlineService {

    /*#################      USER - SECTION     ##############*/

    /**
     * Method to Register a new User
     * @param username
     * @param password
     * @param email
     * @return UserLoginResponse
     * @date 18.05.2015
     * @author Marco
     */
    public UserLoginResponse setUser(String username, String password, String email);


    /**
     * Method to login with Username and Password
     * @param username
     * @param password
     * @return UserLoginResponse
     * @author Marco
     * @date 08.05.2015
     */
    public UserLoginResponse login(String username, String password) throws InvalidLoginException;


    /**
     * Method to log out
     * @throws NoSessionException
     * @author Marco
     * @date 08.05.2015
     */
    public ReturnCodeResponse logout(int sessionID) throws Exception;


    /**
     * Method to get a User by name
     * @author Marco
     * @param sessionId
     * @param userName
     * @return
     */
    public UserResponse getUserByName(int sessionId, String userName);


    //public int createOrUpdateUser(User user);

    /**
     * @author Marco
     * @param sessionId
     * @param username
     * @return
     */
    public ReturnCodeResponse deleteUser(int sessionId, String username);



}
