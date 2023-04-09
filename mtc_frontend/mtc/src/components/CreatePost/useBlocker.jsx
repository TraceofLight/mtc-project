// import { useEffect, useContext } from 'react';
// import { UNSAFE_NavigationContext as NavigationContext } from 'react-router-dom';
// export const useBlocker = (blocker, when = true) => {
//     const navigator = useContext(NavigationContext).navigator;
//     useEffect(() => {
//         if (!when)
//             return;
//         const unblock = navigator.block((tx) => {
//             const autoUnblockingTx = Object.assign(Object.assign({}, tx), { retry() {
//                     unblock();
//                     tx.retry();
//                 } });
//             blocker(autoUnblockingTx);
//         });
//         return unblock;
//     }, [navigator, blocker, when]);
// };