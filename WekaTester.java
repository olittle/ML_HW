import java.io.File;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.core.Instances;
import cs446.weka.classifiers.trees.Id3;

public class WekaTester {

    public static void main(String[] args) throws Exception {

	if (args.length != 1) {
	    System.err.println("Usage: WekaTester arff-file");
	    System.exit(-1);
	}

	// Load the data
	Instances data = new Instances(new FileReader(new File(args[0])));

	// The last attribute is the class label
	data.setClassIndex(data.numAttributes() - 1);

	// Train on 80% of the data and test on 20%
	Instances train = data.trainCV(5,0);
	Instances test = data.testCV(5, 0);

	// Create a new ID3 classifier. This is the modified one where you can
	// set the depth of the tree.
	Id3 classifier = new Id3();

	// An example depth. If this value is -1, then the tree is grown to full
	// depth.
	classifier.setMaxDepth(-1);

	// Train
	classifier.buildClassifier(train);

	// Print the classfier
	System.out.println(classifier);
	System.out.println();

	// Evaluate on the test set
	Evaluation evaluation = new Evaluation(test);
	evaluation.evaluateModel(classifier, test);
	System.out.println(evaluation.toSummaryString());

    }
}
