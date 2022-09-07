/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.models;

import hr.algebra.model.MovieTransferable;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.dal.RepositoryMovie;
import hr.algebra.model.Movie;
import hr.algebra.utils.MessageUtils;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

/**
 *
 * @author frang
 */
public class UserFavoriteMoviePanel extends javax.swing.JPanel {

    RepositoryMovie repositoryMovie;

    private DefaultListModel<Movie> movieModel;

    private DefaultListModel<Movie> favoritePersonModel;

    private Movie selectedFavoriteMovie;

    /**
     * Creates new form UserFavoriteMoviePanel
     */
    public UserFavoriteMoviePanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnDeleteFavoriteMovie = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsMovies = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lsFavoriteMovies = new javax.swing.JList<>();

        setPreferredSize(new java.awt.Dimension(798, 591));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabel1.setText("All movies");

        jLabel2.setText("Favorite movies");

        btnDeleteFavoriteMovie.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnDeleteFavoriteMovie.setForeground(java.awt.Color.red);
        btnDeleteFavoriteMovie.setText("X");
        btnDeleteFavoriteMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteFavoriteMovieActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lsMovies);

        jScrollPane2.setViewportView(lsFavoriteMovies);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteFavoriteMovie))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(btnDeleteFavoriteMovie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        init();
    }//GEN-LAST:event_formComponentShown

    private void btnDeleteFavoriteMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteFavoriteMovieActionPerformed
        try {
            selectedFavoriteMovie = lsFavoriteMovies.getSelectedValue();
            if (selectedFavoriteMovie == null) {
                MessageUtils.showInformationMessage("Select a Movie", "Please Select a Movie");
            } else {
                repositoryMovie.removeFavoriteMovie(selectedFavoriteMovie.getId());
                loadListModels();
            }
        } catch (Exception ex) {
            Logger.getLogger(UserFavoriteMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteFavoriteMovieActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteFavoriteMovie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<Movie> lsFavoriteMovies;
    private javax.swing.JList<Movie> lsMovies;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            initRepo();
            initModels();
            loadListModels();
            initDragNDrop();
        } catch (Exception ex) {
            Logger.getLogger(UserFavoriteMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() throws Exception {
        repositoryMovie = RepositoryFactory.getMovieRepository();
    }

    private void initModels() {
        movieModel = new DefaultListModel<>();
        favoritePersonModel = new DefaultListModel<>();
    }

    private void loadListModels() throws Exception {
        List<Movie> selectMovies = repositoryMovie.selectNotFavoriteMovies();
        movieModel.clear();

        loadListModel(selectMovies, movieModel, lsMovies);

        lsFavoriteMovies.removeAll();
        favoritePersonModel.clear();

        loadListModel(repositoryMovie.selectFavoriteMovies(), favoritePersonModel, lsFavoriteMovies);
    }

    private void loadListModel(List<Movie> selectMovies, DefaultListModel<Movie> movieModel, JList<Movie> lsMovies) {
        selectMovies.forEach(movieModel::addElement);
        lsMovies.setModel(movieModel);
    }

    private void initDragNDrop() {
        lsMovies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsMovies.setDragEnabled(true);
        lsMovies.setTransferHandler(new ExportMovieHandler());

        lsFavoriteMovies.setDropMode(DropMode.ON);
        lsFavoriteMovies.setTransferHandler(new ImportMovieHandler());
    }

    private class ExportMovieHandler extends TransferHandler {

        @Override
        public int getSourceActions(JComponent c) {
            return COPY;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new MovieTransferable(lsMovies.getSelectedValue());
        }

    }

    private class ImportMovieHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(MovieTransferable.MOVIE_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            Transferable transferable = support.getTransferable();
            try {
                Movie add = (Movie) transferable.getTransferData(MovieTransferable.MOVIE_FLAVOR);
                if (!favoritePersonModel.contains(add)) {
                    repositoryMovie.addFavoriteMovie(add.getId());
                    loadListModels();
                    return true;
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(UserFavoriteMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UserFavoriteMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

    }
}