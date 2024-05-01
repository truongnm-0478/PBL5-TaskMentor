export const getColorForLastLetter = (name) => {
    const colors = ['#1A5DB6', '#2E8B57', '#FF8C00', '#FF1493', '#8A2BE2']
    const lastLetter = name.charAt(0).toUpperCase()
    const index = lastLetter.charCodeAt(0) % colors.length
    return colors[index]
}
