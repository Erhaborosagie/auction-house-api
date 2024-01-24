# auction-house-api

This is a suite of services. There are basically 5 services. CD into each directory and start. It uses kafka. 

The flow is:
   - Join a room  - `{room-service}/api/rooms/{roomid}`
   - Submit bid - `{bidding-service}/api/bidding`
   - End bid - `{bidding-service}/api/bidding/{roomId}`
   - make Payment - `{payment-service}//api/payment/{roomId}`
   
There are six possible rooms:

   - room
   - room1
   - room2
   - room3 
   - room4
   - room5

The username is passed as header. Only admin can end bid.