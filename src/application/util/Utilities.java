package application.util;

import application.model.Ingredient;

public class Utilities {

	public static class Ingredients {

		public static boolean isValid(Ingredient ing) {
			String name = ing.getName();
			Number amount = ing.getAmount();
			Number calories = ing.getCalories();
			return !Strings.isEmpty(name) && numberIsValid(amount) && numberIsValid(calories);
		}

		public static boolean numberIsValid(Number number) {
			return number != null && number.doubleValue() > 0;
		}

	}

	public static class Strings {

		public static boolean isEmpty(String s) {
			return s == null || s.length() == 0;
		}

		public static Double parseDouble(String s) {
			try {
				return (isEmpty(s)) ? 0.0 : Double.parseDouble(s);
			} catch (NumberFormatException e) {
				s = s.replace(',', '.');
			}
			return Double.parseDouble(s);
		}

	}

}
