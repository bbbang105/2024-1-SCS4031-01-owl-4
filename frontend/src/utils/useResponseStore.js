import {create} from 'zustand'

const useResponseStore = create(
    set => ({
        responsePortfolio: {},
        responseBackTest: {},
        loading: false,
        prevPortfolioId: '',
        description: '',
        title: '',
        fund: 1000000,
        candle: 'days',
        m_date: '60',
        n_date: '20',
        buyingCondition: 5,
        sellingCondition: 3,
        buyingSplit: 20,
        stopLossPoint: 10,
        isChecked: false,
        access_key: '',
        secret_key: '',
        comment: '',
        isppc: false,
        issc: false,
        ispic: false,
        coin_name: '비트코인',
        market_name: 'KRW-BTC',
        errIpState: false,
        errKeyState: false,
        showAPI: false,
        showAPISecret: false,
        isAPISuccess: false,
        isAPISuccessFirst: false,
        start: 95,
        end: 100,
        volume: [],
        dateData: [],
        coinData: [],
        autoInfo: [],
        isAutoTrade: false,
        username: '',
        setUsername: username => set({username}),
        setIsAutoTrade: isAutoTrade => set({isAutoTrade}),
        setAutoInfo: autoInfo => set({autoInfo}),
        setCoinData: coinData => set({coinData}),
        setDD: dateData => set({dateData}),
        setVV: volume => set({volume}),
        setStart: start => set({start}),
        setEnd: end => set({end}),
        setIsAPISuccessFirst: isAPISuccessFirst => set({isAPISuccessFirst}),
        setIsAPISuccess: isAPISuccess => set({isAPISuccess}),
        setErrIpState: errIpState => set({errIpState}),
        setErrKeyState: errKeyState => set({errKeyState}),
        setShowAPI: showAPI => set({showAPI}),
        setShowAPISecret: showAPISecret => set({showAPISecret}),
        setResponseBackTest: responseBackTest => set({responseBackTest}),
        setResponsePortfolio: responsePortfolio => set({responsePortfolio}),
        setLoading: () => set((state) => ({loading: !state.loading})),
        setPrevPortfolioId: prevPortfolioId => set({prevPortfolioId}),
        setDescription: description => set({description}),
        setTitle: title => set({title}),
        setFund: fund => set({fund}),
        setCandle: candle => set({candle}),
        setMdate: m_date => set({m_date}),
        setNdate: n_date => set({n_date}),
        setBuyingCondition: buyingCondition => set({buyingCondition}),
        setSellingCondition: sellingCondition => set({sellingCondition}),
        setBuyingSplit: buyingSplit => set({buyingSplit}),
        setStopLossPoint: stopLossPoint => set({stopLossPoint}),
        setIsChecked: isChecked => set({isChecked}),
        setAccessKey: access_key => set({access_key}),
        setSecretKey: secret_key => set({secret_key}),
        setComment: comment => set({comment}),
        setIsPPC: isppc => set({isppc}),
        setIsSC: issc => set({issc}),
        setIsPIC: ispic => set({ispic}),
        setCoinName: coin_name => set({coin_name}),
        setMarketName: market_name => set({market_name})
    })
)

export default useResponseStore


