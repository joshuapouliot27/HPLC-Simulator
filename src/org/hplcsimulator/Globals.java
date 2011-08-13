package org.hplcsimulator;

import javax.help.HelpBroker;
import javax.help.HelpSet;

public class Globals
{
	public static HelpSet hsMainHelpSet = null;
	public static HelpBroker hbMainHelpBroker = null;
	public static String[] SolventAArray = {
		"Water"
	};
	public static String[] SolventBArray = {
		"Acetonitrile",
		"Methanol"
	};
	public static String[] CompoundNameArray = {
		"n-benzyl formamide",
		"benzylalcohol",
		"phenol",
		"3-phenyl propanol",
		"acetophenone",
		"benzonitrile",
		"p-chlorophenol",
		"nitrobenzene",
		"methyl benzoate",
		"anisole",
		"benzene",
		"p-nitrotoluene",
		"p-nitrobenzyl chloride",
		"toluene",
		"benzophenone",
		"bromobenzene",
		"naphthalene",
		"ethylbenzene",
		"p-xylene",
		"p-dichlorobenzene",
		"propylbenzene",
		"n-butylbenzene"
	};
	/* The LSSTDataArray is organized as [compound][eluent (0 = ACN, 1 = MeOH)][parameter (0 = Logk'w vs. T slope, 1 = Log k'w vs. T intercept, 2 = S vs. T slope, 3 = S vs. T intercept)]*/
	public static double[][][] LSSTDataArray = {
		/*N-benzyl formamide*/		{{-0.003731062, 0.769127502, 0.001962312, -1.950157208}, {-0.008930, 1.540840, 0.007655, -2.693812}},
		/*benzylalcohol*/			{{-0.00364158,  0.870975072, 0.001538701, -1.801193074}, {-0.008879, 1.643379, 0.007510, -2.603915}},
		/*phenol*/ 					{{-0.007051397, 1.222652803, 0.004948239, -2.157819856}, {-0.010465, 1.714002, 0.009040, -2.668850}},
		/*3-phenyl propanol*/		{{-0.005175387, 1.617423196, 0.004245094, -2.711627278}, {-0.012422, 2.682599, 0.010667, -3.544294}},
		/*acetophenone*/			{{-0.006113393, 1.615282733, 0.004190421, -2.419171414}, {-0.009257, 2.098172, 0.008684, -2.981032}},
		/*benzonitrile*/			{{-0.008118482, 1.759520682, 0.006344149, -2.581132813}, {-0.009409, 1.995681, 0.009057, -2.971312}},
		/*p-chlorophenol*/			{{-0.009910541, 2.006666967, 0.009176534, -3.110178571}, {-0.015142, 2.752714, 0.013319, -3.575982}},
		/*nitrobenzene*/			{{-0.009433757, 2.074051745, 0.008291834, -2.909075892}, {-0.010864, 2.277843, 0.009471, -2.982885}},
		/*methyl benzoate*/			{{-0.0077323,   2.116364506, 0.006053077, -2.911497882}, {-0.011888, 2.754816, 0.010739, -3.478031}},
		/*anisole*/					{{-0.008804318, 2.2070705,   0.007554149, -2.941605639}, {-0.011362, 2.587045, 0.009682, -3.103432}},
		/*benzene*/					{{-0.008529343, 2.208069224, 0.007114051, -2.82653686},  {-0.010915, 2.574300, 0.009507, -3.003558}},
		/*p-nitrotoluene*/			{{-0.010705359, 2.565390235, 0.009864271, -3.433694873}, {-0.012887, 2.900254, 0.011005, -3.522154}},
		/*p-nitrobenzyl chloride*/	{{-0.012956701, 2.804538425, 0.012986999, -3.866695802}, {-0.014804, 3.063453, 0.013295, -3.802081}},
		/*toluene*/					{{-0.010041886, 2.738254098, 0.009005662, -3.393491059}, {-0.013100, 3.230992, 0.010959, -3.547073}},
		/*benzophenone*/			{{-0.011069419, 3.087847385, 0.010362441, -4.064139779}, {-0.015610, 3.814708, 0.014480, -4.572059}},
		/*bromobenzene*/			{{-0.011273051, 2.941762836, 0.010618543, -3.651869929}, {-0.015204, 3.539528, 0.013731, -3.937992}},
		/*naphthalene*/				{{-0.012795634, 3.296888486, 0.012596045, -4.112896456}, {-0.017658, 3.996277, 0.015853, -4.364601}},
		/*ethylbenzene*/			{{-0.011878845, 3.278713293, 0.01150122,  -4.00434081},  {-0.016026, 3.915079, 0.014007, -4.212582}},
		/*p-xylene*/				{{-0.011333502, 3.257874421, 0.01022372,  -3.911260542}, {-0.015301, 3.898820, 0.012295, -4.091578}},
		/*p-dichlorobenzene*/		{{-0.01247399,  3.351026587, 0.011894773, -4.06721018},  {-0.016725, 4.042718, 0.014898, -4.384667}},
		/*propylbenzene*/			{{-0.012995521, 3.811104371, 0.012122284, -4.521888293}, {-0.017711, 4.572339, 0.014763, -4.776607}},
		/*n-butylbenzene*/			{{-0.013247488, 4.268938682, 0.011228978, -4.916062651}, {-0.019604, 5.195287, 0.015945, -5.293528}}
	};
	public static double[] MolarVolumeArray = {
		/*N-benzyl formamide*/		156.1,
		/*benzylalcohol*/			125.6,
		/*phenol*/					103.4,
		/*3-phenyl propanol*/		170,
		/*acetophenone*/			140.4,
		/*benzonitrile*/			122.7,
		/*p-chlorophenol*/			124.3,
		/*nitrobenzene*/			122.7,
		/*methyl benzoate*/			151.2,
		/*anisole*/					128.1,
		/*benzene*/					96,
		/*p-nitrotoluene*/			144.9,
		/*p-nitrobenzyl chloride*/	165.8,
		/*toluene*/					118.2,
		/*benzophenone*/			206.8,
		/*bromobenzene*/			119.3,
		/*naphthalene*/				147.6,
		/*ethylbenzene*/			140.4,
		/*p-xylene*/				140.4,
		/*p-dichlorobenzene*/		137.8,
		/*propylbenzene*/			162.6,
		/*n-butylbenzene*/			184.8
	};
}
