export function LogOutService() {
    localStorage.removeItem('Token');
    localStorage.setItem('isLoggedIn', 'false');
}
