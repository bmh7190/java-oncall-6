package oncall.config;

import oncall.controller.ScheduleController;
import oncall.service.ScheduleService;
import oncall.view.InputView;
import oncall.view.OutputView;

public class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();

    private InputView inputView;
    private OutputView outputView;

    private ScheduleService service;
    private ScheduleController controller;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public ScheduleController controller() {
        if (controller == null) {
            controller = new ScheduleController(
                    inputView(),
                    outputView(),
                    service()
            );
        }
        return controller;
    }

    protected ScheduleService service() {
        if (service == null) {
            service = new ScheduleService();
        }
        return service;
    }

    protected InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    protected OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }
}