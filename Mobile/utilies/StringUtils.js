export const getLastLetter = (name) => {
    const words = name.trim().split(/\s+/);
    if (words.length < 2) {
        return ''
    }
    const lastWord = words[words.length - 1];
    const secondLastWord = words[words.length - 2];
    return `${secondLastWord.charAt(0)}${lastWord.charAt(0)}`.toUpperCase();
}
