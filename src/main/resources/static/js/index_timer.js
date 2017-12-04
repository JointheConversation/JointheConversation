// "use strict";
// var date;
// var display = document.getElementById('time');
//
// $(document).ready(function() {
//     getTime('GMT', function(time){
//         date = new Date(time);
//     });
// });
//
// setInterval(function() {
//     date = new Date(date.getTime() + 1000);
//
//     var currenthours = date.getHours();
//     var currentSecs = date.getSeconds();
//     var hours;
//     var minutes;
//     var seconds;
//     if (currenthours == 23 && currentsecs == 0){
//         display.innerHTML = 'LIVE NOW';
//     } else {
//         if (currenthours < 23) {
//             hours = 22 - currenthours;
//         } else {
//             hours = 23;
//         }
//         minutes = 60 - date.getMinutes();
//         seconds = 60 - date.getSeconds();
//
//         if (minutes < 10) {
//             minutes = '0' + minutes;
//         }
//         if (seconds < 10) {
//             seconds = '0' + seconds;
//         }
//         display.innerHTML = hours + ':' + minutes + ':' +seconds;
//     }
// }, 1000);



