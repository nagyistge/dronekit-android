/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */
         
// MESSAGE GIMBAL_REQUEST_AXIS_CALIBRATION PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;
import com.MAVLink.ardupilotmega.CRC;
import java.nio.ByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
* 
			Requests any currently uncalibrated gimbal axes to run the axis calibration procedure.  An axis is considered uncalibrated if its commutation calibration
			slope and intercept are 0
		
*/
public class msg_gimbal_request_axis_calibration_test{

public static final int MAVLINK_MSG_ID_GIMBAL_REQUEST_AXIS_CALIBRATION = 213;
public static final int MAVLINK_MSG_LENGTH = 2;
private static final long serialVersionUID = MAVLINK_MSG_ID_GIMBAL_REQUEST_AXIS_CALIBRATION;

private Parser parser = new Parser();

public CRC generateCRC(byte[] packet){
    CRC crc = new CRC();
    for (int i = 1; i < packet.length - 2; i++) {
        crc.update_checksum(packet[i] & 0xFF);
    }
    crc.finish_checksum(MAVLINK_MSG_ID_GIMBAL_REQUEST_AXIS_CALIBRATION);
    return crc;
}

public byte[] generateTestPacket(){
    ByteBuffer payload = ByteBuffer.allocate(6 + MAVLINK_MSG_LENGTH + 2);
    payload.put((byte)MAVLinkPacket.MAVLINK_STX); //stx
    payload.put((byte)MAVLINK_MSG_LENGTH); //len
    payload.put((byte)0); //seq
    payload.put((byte)255); //sysid
    payload.put((byte)190); //comp id
    payload.put((byte)MAVLINK_MSG_ID_GIMBAL_REQUEST_AXIS_CALIBRATION); //msg id
    payload.put((byte)5); //target_system
    payload.put((byte)72); //target_component
    
    CRC crc = generateCRC(payload.array());
    payload.put((byte)crc.getLSB());
    payload.put((byte)crc.getMSB());
    return payload.array();
}

@Test
public void test(){
    byte[] packet = generateTestPacket();
    for(int i = 0; i < packet.length - 1; i++){
        parser.mavlink_parse_char(packet[i] & 0xFF);
    }
    MAVLinkPacket m = parser.mavlink_parse_char(packet[packet.length - 1] & 0xFF);
    byte[] processedPacket = m.encodePacket();
    assertArrayEquals("msg_gimbal_request_axis_calibration", processedPacket, packet);
}
}
        