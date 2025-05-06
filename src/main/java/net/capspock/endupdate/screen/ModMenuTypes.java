package net.capspock.endupdate.screen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.screen.custom.VoidInfuserMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, EndUpdate.MOD_ID);

    public static final RegistryObject<MenuType<VoidInfuserMenu>> VOID_INFUSER_MENU =
            MENUS.register("void_infuser_menu", () -> IForgeMenuType.create(VoidInfuserMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
