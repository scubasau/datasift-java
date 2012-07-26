/**
 * This example creates a push subscription in your account subscribed to a
 * stream from the CSDL provided.
 */
package org.datasift.examples.push;

import java.io.IOException;

import org.datasift.Definition;
import org.datasift.EAPIError;
import org.datasift.EAccessDenied;
import org.datasift.EInvalidData;
import org.datasift.PushDefinition;
import org.datasift.PushSubscription;
import org.datasift.examples.Utils;

public class PushStreamFromCSDL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Set up the environment
		Env.init(args);
		
		// Check we have enough arguments
		if (Env.getArgCount() < 3) {
			usage();
		}

		// Fixed args
		String csdl_filename = Env.getArg(0);
		String output_type   = Env.getArg(1);
		String name          = Env.getArg(2);
		
		// Read the CSDL from the file
		String csdl = "";
		try {
			csdl = Utils.readFileAsString(csdl_filename);
		} catch (IOException e1) {
			System.err.println("Failed to read the CSDL from " + csdl_filename);
			System.exit(1);
		}
		
		// Create the stream definition
		Definition streamDef = Env.getUser().createDefinition(csdl);
		
		// The rest of the args will be output parameters, and we'll use them later
		
		try {
			// Create the PushDefinition
			PushDefinition pushDef = Env.getUser().createPushDefinition();
			pushDef.setName(name);
			pushDef.setOutputType(output_type);
			
			// Now add the output_type-specific args from the command line
			for (int i = 3; i < Env.getArgCount(); i++) {
				String[] bits = Env.getArg(i).split("=", 2);
				if (bits.length != 2) {
					usage("Invalid output_param: " + Env.getArg(i));
				}
				pushDef.setOutputParam(bits[0], bits[1]);
			}

			// Subscribe the push definition to the stream definition
			PushSubscription pushSub = pushDef.subscribe(streamDef);

			// Display the details of the new subscription
			Env.displaySubscriptionDetails(pushSub);
		} catch (EInvalidData e) {
			System.err.println("EInvalidData: " + e.getMessage());
		} catch (EAPIError e) {
			System.err.println("EAPIError: " + e.getMessage());
		} catch (EAccessDenied e) {
			System.err.println("EAccessDenied: " + e.getMessage());
		}
		
	}
	
	public static void usage() {
		usage("", true);
	}
	
	public static void usage(String message) {
		usage(message, true);
	}
	
	public static void usage(String message, boolean exit) {
		if (message.length() > 0) {
			System.err.println("");
			System.err.println(message);
		}
		System.err.println("");
		System.err.println("Usage: PushStreamFromCSDL csdl_filename output_type name ...");
		System.err.println("");
		System.err.println("Where: csdl_filename = a file containing the CSDL");
		System.err.println("       output_type   = http (currently only http is supported)");
		System.err.println("       name          = a friendly name for the subscription");
		System.err.println("       key=val       = output_type-specific arguments");
		System.err.println("");
		System.err.println("Example");
		System.err.println("       PushStreamFromCSDL csdl.txt http \"Push Name\" delivery_frequency=10 \\");
		System.err.println("                    url=http://www.example.com/push_endpoint auth.type=none");
		System.err.println("");
		if (exit) {
			System.exit(1);
		}
	}

}
