const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');


module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    Index: path.resolve(__dirname, 'src', 'pages', 'Index.js'),
    AccountCreation: path.resolve(__dirname, 'src', 'pages', 'AccountCreation.js'),
    AccountSignIn: path.resolve(__dirname, 'src', 'pages', 'AccountSignIn.js'),
    EventPageOrganizer: path.resolve(__dirname, 'src', 'pages', 'EventPageOrganizer.js'),
    EventViewPageUser: path.resolve(__dirname, 'src', 'pages', 'EventViewPageUser.js'),
    EventViewPageVisitor: path.resolve(__dirname, 'src', 'pages', 'EventViewPageVisitor.js'),
    FriendPage: path.resolve(__dirname, 'src', 'pages', 'FriendPage.js'),
    UserProfile: path.resolve(__dirname, 'src', 'pages', 'UserProfile.js'),
    Notification: path.resolve(__dirname, 'src', 'pages', 'NotificationPage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    proxy: [
      {
        context: [
          '/User',
          '/Event'
        ],
        target: 'http://localhost:5001'
      }
    ]
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/AccountCreation.html',
      filename: 'AccountCreation.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/AccountSignIn.html',
      filename: 'AccountSignIn.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/EventOrganizer.html',
      filename: 'EventOrganizer.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/EventViewUser.html',
      filename: 'EventViewUser.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/EventViewVisitor.html',
      filename: 'EventViewVisitor.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/FriendsPage.html',
      filename: 'FriendsPage.html',
      inject: false
    }),
     new HtmlWebpackPlugin({
       template: './src/UserProfile.html',
       filename: 'UserProfile.html',
       inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/Notification.html',
      filename: 'Notification.html',
      inject: false
    }),
     /*new HtmlWebpackPlugin({
          template: './src/index.html',//change to my page - do this for every page, yes
          filename: 'index.html',
          inject: false
        }),
        */

    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}