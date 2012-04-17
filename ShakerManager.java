/*
 * AERILYS 2012
 * http://www.aerilys.fr
 * Android class to manage a shake from an Android device
 * It can be udapted to manage shake on other plateform
 */
public abstract class ShakerManager
{
	/*
	 * Fields of actual accelerations in three components of a vector
	 */
	private static float xAccel;
	private static float yAccel;
	private static float zAccel;

	/*
	 * Old accelerations
	 */
	private static float xPreviousAccel;
	private static float yPreviousAccel;
	private static float zPreviousAccel;

	/*
	 * A boolean to know if it's first update of shake or not
	 */
	private static boolean firstUpdate = true;

	/*
	 * Sensibility of the sensor
	 */
	private static final float shakeThreshold = 3f;

	/*
	 * Boolean to know if a shake is initiated or not
	 */
	private static boolean shakeInitiated = false;

	/*
	 * Call this method each time you have a change in your device's movement
	 * Return : true if a shake is done, else false
	 */
	public static boolean update(float xNewAccel, float yNewAccel, float zNewAccel)
	{
		// Permit to update accelerations
		updateAccel(xNewAccel, yNewAccel, zNewAccel);

		/*
		 * If there is not shake but acceleration has changed, a shake is initiated
		 */
		if ((!shakeInitiated) && isAccelerationChanged())
		{
			shakeInitiated = true;
		}
		/*
		 * If shake is initiated and acceleration changed, return true : it's shaking !
		 */
		else if ((shakeInitiated) && isAccelerationChanged())
		{
			return true;
		}
		/*
		 * If you've a shake but no acceleration change, you've to "turn off" your shake
		 */
		else if ((shakeInitiated) && (!isAccelerationChanged()))
		{
			shakeInitiated = false;
		}
		
		return false;
	}

	/*
	 * Update the coords of acceleration
	 */
	private static void updateAccel(float xNewAccel, float yNewAccel, float zNewAccel)
	{
		if (firstUpdate)
		{
			xPreviousAccel = xNewAccel;
			yPreviousAccel = yNewAccel;
			zPreviousAccel = zNewAccel;
			firstUpdate = false;
		}
		else
		{
			xPreviousAccel = xAccel;
			yPreviousAccel = yAccel;
			zPreviousAccel = zAccel;
		}
		xAccel = xNewAccel;
		yAccel = yNewAccel;
		zAccel = zNewAccel;
	}

	/*
	 * Permit to know if the acceleration is changing of sense
	 */
	private static boolean isAccelerationChanged()
	{
		float deltaX = Math.abs(xPreviousAccel - xAccel);
		float deltaY = Math.abs(yPreviousAccel - yAccel);
		float deltaZ = Math.abs(zPreviousAccel - zAccel);
		return (deltaX > shakeThreshold && deltaY > shakeThreshold)
				|| (deltaX > shakeThreshold && deltaZ > shakeThreshold)
				|| (deltaY > shakeThreshold && deltaZ > shakeThreshold);
	}
}
