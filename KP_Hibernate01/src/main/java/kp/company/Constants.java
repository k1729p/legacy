package kp.company;

import org.eclipse.swt.layout.GridData;

/**
 * Class Constants.
 */
public interface Constants {

	/*
	 * Configuration
	 */
	String LOGGING_PROPS_FILE = "/logging.properties";

	/*
	 * GUI
	 */
	enum Layout {
		BUTTON(65, 25), BUTTON_WIDE(130, 25);

		Layout(int width, int height) {
			this.gridData = new GridData(width, height);
		}

		private GridData gridData = null;

		public GridData getValue() {
			return gridData;
		}
	}

	enum ColumnWidth {
		SHORT(50), NORMAL(100), WIDE(150);

		ColumnWidth(int value) {
			this.value = value;
		}

		private final int value;

		public int getValue() {
			return value;
		}
	}

	int COMBO_WIDTH = 234;
	int TEXT_WIDTH = 250;
}