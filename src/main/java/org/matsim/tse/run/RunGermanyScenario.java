package org.matsim.tse.run;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.MutableScenario;
import org.matsim.core.scenario.ScenarioUtils;

public class RunGermanyScenario {

    private static final Logger logger = Logger.getLogger(RunGermanyScenario.class);

    public static void main(String[] args) {
/*        logger.info("Started the Microsimulation Transport Orchestrator (MITO) based on 2017 models");
        MitoModelGermany model = MitoModelGermany.standAloneModel(args[0], MunichImplementationConfig.get());
        model.run();
        final DataSet dataSet = model.getData();*/

        boolean runAssignment = true;

        if (runAssignment) {
            logger.info("Running traffic assignment in MATsim");

            Config config;
            if (args.length > 1 && args[1] != null) {
                config = ConfigUtils.loadConfig(args[1]);
                ConfigureMatsim.setDemandSpecificConfigSettings(config);
            } else {
                logger.warn("Using a fallback config with default values as no initial config has been provided.");
                config = ConfigureMatsim.configureMatsim();
            }

            config.controler().setOutputDirectory("scenarios/tse/base");
            config.network().setInputFile("/home/tumtse/Documents/haowu/MSM/matsim-germany_msm/matsim-format/final-version/network_with-rail.xml.gz");
            config.transit().setTransitScheduleFile("/home/tumtse/Documents/haowu/MSM/matsim-germany_vsp/Germany/input/2020_Train_GTFS_transitSchedule_cleaned.xml.gz");
            config.transit().setVehiclesFile("/home/tumtse/Documents/haowu/MSM/matsim-germany_vsp/Germany/input/2020_Train_GTFS_transitVehicles_cleaned.xml.gz");
            config.plans().setInputFile("/home/tumtse/Documents/haowu/MSM/matsim-germany_msm/matsim-format/short-distance_Hao/trips_germanySD_2030_0.1percent.xml.gz");

            //MutableScenario matsimScenario = (MutableScenario) ScenarioUtils.loadScenario(config);
            Scenario matsimScenario = ScenarioUtils.loadScenario(config) ;
            Controler controler = new Controler(matsimScenario);
            controler.run();

        }
    }
}