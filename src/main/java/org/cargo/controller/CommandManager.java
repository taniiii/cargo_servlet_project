package org.cargo.controller;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
/**
 * This class handles mapping of the url paths to the commands.
 */
public class CommandManager {
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);

    private Map<String, Command> commandMap;

    public CommandManager() {
        LOGGER.debug("Initializing CommandManager");

        commandMap = new HashMap<>();

        commandMap.put("/", new GetHomePageCommand());
        commandMap.put("/login", new GetLoginPageCommand());
        commandMap.put("/registration", new GetRegistrationPageCommand());
        commandMap.put("/tariffs", new GetTariffPageCommand());
        commandMap.put("/user/profile", new GetUserProfileCommand());
        commandMap.put("/admin/userlist", new GetUserListPageCommand());
        commandMap.put("/admin/user", new GetUserEditFormCommand());
        commandMap.put("/user/showNewTransportationForm", new GetNewTransportationFormCommand());
        commandMap.put("/admin/orders", new GetAllTransportationsPageCommand());
        commandMap.put("/admin/transportation", new GetTransportationEditFormCommand());
        commandMap.put("/user/orders", new GetUserTransportationsPageCommand());
        commandMap.put("/user/transp", new GetPaymentConfirmPageCommand());
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/admin/user/edit", new UpdateUserCommand());
        commandMap.put("/user/saveTransportation", new NewTransportationCommand());
        commandMap.put("/admin/transportationConfirm", new UpdateTransportationCommand());
        commandMap.put("/user/paymentComplete", new PaymentCompleteCommand());

        LOGGER.debug("Command container was successfully initialized");
    }

    /**
     * This method is used to get a command instance mapped to http get or post method, based on a request.
     */
    public Command getCommand(String path) {
        return commandMap.getOrDefault(path, new GetHomePageCommand());
    }

}
