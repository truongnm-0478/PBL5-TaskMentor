import {getLastLetter} from '@/utils/stringUtils.js'

export const getColorForLastLetter = (name) => {
    const colors = [
        '#1A5DB6', '#2E8B57', '#FF8C00', '#FF1493', '#8A2BE2', '#4880FF',
        '#FF6347', '#00CED1', '#4285f4', '#2F4F4F', '#FF4500', '#556B2F',
        '#4682B4', '#800000', '#9370DB', '#32CD32', '#8B4513', '#7B68EE',
        '#34a853', '#000080', '#FFD700', '#fbbc05', '#4B0082', '#808080',
        '#00FF00', '#1E90FF', '#b3d133', '#1bb7e7', '#008080', '#DAA520',
        '#ADFF2F', '#FF69B4', '#B0E0E6', '#1bb7e7', '#20B2AA', '#778899',
        '#40E0D0', '#87CEEB', '#ea4335', '#6495ED', '#FF00FF', '#7FFF00',
        '#00FA9A', '#008B8B', '#00FFFF', '#7CFC00', '#0000FF', '#FF7F50',
        '#008000', '#FAFAD2', '#228B22'
    ]
    const index = name.charCodeAt(0) % colors.length;
    return colors[index];
}

