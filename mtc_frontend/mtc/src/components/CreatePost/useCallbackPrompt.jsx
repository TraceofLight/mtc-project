// import { useCallback, useState } from 'react';
// import { useLocation } from 'react-router';
// import { useBlocker } from './useBlocker';
// export const useCallbackPrompt = (when) => {
//     const location = useLocation();
//     const [showPrompt, setShowPrompt] = useState(false);
//     const [blockedLocation, setBlockedLocation] = useState(null);
//     const cancelNavigation = useCallback(() => {
//         setShowPrompt(false);
//         setBlockedLocation(null);
//     }, []);
//     const blocker = useCallback((tx) => {
//         if (tx.location.pathname !== location.pathname) {
//             setBlockedLocation(tx);
//             setShowPrompt(true);
//         }
//     }, [location]);
//     const confirmNavigation = useCallback(() => {
//         if (blockedLocation) {
//             blockedLocation.retry();
//             cancelNavigation(); // 클린업
//         }
//     }, [blockedLocation]);
//     useBlocker(blocker, when);
//     return [showPrompt, confirmNavigation, cancelNavigation];
// };