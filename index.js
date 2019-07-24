const { ApolloServer } = require("apollo-server");
const { ApolloGateway } = require("@apollo/gateway");

const gateway = new ApolloGateway({
	serviceList: [
		{ name: "backend", url: "http://localhost:8080/api/graphql" },
	]
});

(async () => {
	const { schema, executor } = await gateway.load();
	console.log(schema);

	const server = new ApolloServer({ schema, executor });

	server.listen().then(({ url }) => {
		console.log(`🚀 Server ready at ${url}`);
	});
})();
