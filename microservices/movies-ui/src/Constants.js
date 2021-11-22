const prod = {
  url: {
    KEYCLOAK_BASE_URL: "https://movie.skycomposer.net",
    API_BASE_URL: 'https://movie.skycomposer.net/movies-api',
    OMDB_BASE_URL: 'https://www.omdbapi.com',
    AVATARS_DICEBEAR_URL: 'https://avatars.dicebear.com/api'
  }
}

const dev = {
  url: {
    KEYCLOAK_BASE_URL: "https://movie.skycomposer.net",
    API_BASE_URL: 'https://movie.skycomposer.net/movies-api',
    OMDB_BASE_URL: 'https://www.omdbapi.com',
    AVATARS_DICEBEAR_URL: 'https://avatars.dicebear.com/api'
  }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod