package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 *
 *
 * This class handles mapping of the url paths to the commands.
 */
public class CommandManager {
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);

    private Map<String, Command> getCommands;
    private Map<String, Command> postCommands;

    public CommandManager() {
        LOGGER.debug("Initializing CommandManager");

        getCommands = new HashMap<>();
        postCommands = new HashMap<>();

        getCommands.put("/", new GetHomePageCommand());
        getCommands.put("/login", new GetLoginPageCommand());
        getCommands.put("/registration", new GetRegistrationPageCommand());
        getCommands.put("/tariffs", new GetTariffPageCommand());
        getCommands.put("/user/profile", new GetUserProfileCommand());
        getCommands.put("/admin/userlist", new GetUserListPageCommand());
        getCommands.put("/admin/user", new GetUserEditFormCommand());
        getCommands.put("/user/showNewTransportationForm", new GetNewTransportationFormCommand());
        getCommands.put("/admin/orders", new GetAllTransportationsPageCommand());
        getCommands.put("/admin/transportation", new GetTransportationEditFormCommand());
        getCommands.put("/user/orders", new GetUserTransportationsPageCommand());
        getCommands.put("/user/transp", new GetPaymentConfirmPageCommand());


        postCommands.put("/login", new LoginCommand());
        postCommands.put("/registration", new RegistrationCommand());
        postCommands.put("/logout", new LogoutCommand());
        postCommands.put("/user/profile", new UpdateProfileCommand());
        postCommands.put("/admin/user/edit", new UpdateUserCommand());
        postCommands.put("/user/saveTransportation", new NewTransportationCommand());
        postCommands.put("/admin/transportationConfirm", new UpdateTransportationCommand());
        postCommands.put("/user/paymentComplete", new PaymentCompleteCommand());

        MappingProperties properties = MappingProperties.getInstance();

        LOGGER.debug("Command container was successfully initialized");
    }

    /**
     * This method is used to get a command instance mapped to http get method, based on a request.
     *
     *
     */
    public Command getGetCommand(HttpServletRequest request) {
        String command = getMapping(request);
        LOGGER.debug("Trying to get " + command);

        if (getCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return getCommands.get(command);
    }

    /**
     * This method is used to get a command instance mapped to http post method, based on a request.
     *
     *
     */
    public Command getPostCommand(HttpServletRequest request) {
        String command = getMapping(request);
        LOGGER.debug("Trying to get command " + command);

        if (postCommands.get(command) == null) {
            return getCommands.get("/");
        }

        return postCommands.get(command);
    }

    /**
     * This is a helper method to get command mapping from uri.
     *
     *
     */
    public String getMapping(HttpServletRequest request) {
        String mapping = request.getRequestURI().substring(request.getContextPath().length());
        if (mapping.endsWith("/")) {
            mapping = mapping.substring(0, mapping.length() - 1);
        }
        return mapping;
    }
}
