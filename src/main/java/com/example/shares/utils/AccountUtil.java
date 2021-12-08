package com.example.shares.utils;

import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.interfaces.NoArgNoReturnHandler;

/**
 * @author lijiawei
 */
public class AccountUtil {

    public static void autoContextForeach(NoArgNoReturnHandler noArgNoReturnHandler) {
        RuntimeDataConstants.ACCOUNT_CONTAINER.forEach(account -> {
            UserInfoContext.set(account);
            noArgNoReturnHandler.handle();
        });
    }

}