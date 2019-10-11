const functions = require('firebase-functions');

//Initializes the app
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase); 

// Listens to when the node "NotificationsFirebase" is written to.
exports.sendNewHeroNotification = functions
.firestore
.document('/events/{eventId}')
.onUpdate((change) => {
    // Je récupère les données de l'événement avant et après modification
    const oldEvent = change.before.data();
    const newEvent = change.after.data();
    const userAskId = newEvent.userAskId;
    const userToken = newEvent.userAsk.userToken   
    console.log("userAskId: " + userAskId)
    console.log("userToken: " + userToken)

    //----------------------------------------------------------------------------------    
    // Cas où un héros s'est proposé
    if (newEvent.numberHeroNotFound < oldEvent.numberHeroNotFound) {
    console.log("Add: il faut envoyer une notification à l'utilisateur: " + userAskId)
    console.log("Add: Son token est: " + userToken)

    const payload = {
        data: {
            title: 'Un nouveau héros veut vous aider',
            body: 'Cliquez pour en savoir plus',
            KEYNOTIF: 'heroAdded'
        }
    };

    return admin.messaging().sendToDevice(userToken, payload)
    .then(response => {
        var db = admin.firestore();
        console.log(response)
          // For each message check if there was an error.
          const tokensToRemove = [];
          response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
              console.error('Failure sending notification to', tokens[index], error);
              // Cleanup the tokens who are not registered anymore.
              if (error.code === 'messaging/invalid-registration-token' ||
                  error.code === 'messaging/registration-token-not-registered') {
              tokensToRemove.push(pushToken);
              }
            }
         });
         return Promise.all(tokensToRemove);
       });
        
    
} else { 
    // Cas où le demandeur supprime un héros
    if (newEvent.numberHeroNotFound > oldEvent.numberHeroNotFound) {
        const toDeleteToken = newEvent.deletedHeroToken;
        
        console.log("Delete: il faut envoyer une notification à l'utilisateur dont le token est: " + toDeleteToken)

        const payload2 = {
            data: {
                title: 'Mission annulée',
                body: 'Un lanceur de SOS a annulé votre participation à sa mission.',
                KEYNOTIF: 'heroDeleted'
            }
        };
    
        return admin.messaging().sendToDevice(toDeleteToken, payload2)
} else {
    return false;
}
}
});
